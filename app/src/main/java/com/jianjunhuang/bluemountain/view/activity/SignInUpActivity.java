package com.jianjunhuang.bluemountain.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.SignInUpContact;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.jianjunhuang.bluemountain.presenter.SignInUpPresenter;
import com.jinjunhuang.loadingcirclebtn.LoadingCircleBtn;

import org.w3c.dom.Text;

public class SignInUpActivity extends BaseActivity
        implements View.OnClickListener, SignInUpContact.View, TextWatcher {

    private TextInputEditText userNameEdt;
    private TextInputEditText pwdEdt;
    private TextInputEditText ensurePwdEdt;
    private LoadingCircleBtn loginBtn;
    private Button switchBtn;
    private TextInputLayout ensurePwdInputLayout;
    private boolean isLoginStatus = true;
    private SpannableString signInSpanStr;
    private SpannableString signUpSpanStr;
    private SignInUpPresenter mPresenter;
    private TextView tipsTv;

    @Override
    protected int getLayoutId() {
        return R.layout.sign_in_up_activity;
    }

    @Override
    protected void initView() {
        mPresenter = new SignInUpPresenter(this);
        tipsTv = findView(R.id.login_tips_tv);
        userNameEdt = findView(R.id.login_user_name_edt);
        pwdEdt = findView(R.id.login_pwd_edt);
        ensurePwdEdt = findView(R.id.login_ensure_pwd_edt);
        switchBtn = findView(R.id.login_switch_btn);
        loginBtn = findView(R.id.login_btn);
        ensurePwdInputLayout = findView(R.id.login_ensure_pwd_input_layout);
        initTextSpannableString();
        switchLoginStatus(true);
    }

    private void initTextSpannableString() {
        String signUp = getResources().getString(R.string.sign_up);
        signUpSpanStr = new SpannableString(signUp);
        signUpSpanStr.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorGreen)),
                signUp.length() - 2, signUp.length(), 0);

        String signIn = getResources().getString(R.string.sign_in);
        signInSpanStr = new SpannableString(signIn);
        signInSpanStr.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorBlue)),
                signIn.length() - 2, signIn.length(), 0);
    }

    private void switchLoginStatus(boolean isLoginStatus) {
        this.isLoginStatus = isLoginStatus;
        if (isLoginStatus) {
            ensurePwdInputLayout.setVisibility(View.GONE);
            switchBtn.setText(signUpSpanStr);
        } else {
            ensurePwdInputLayout.setVisibility(View.VISIBLE);
            switchBtn.setText(signInSpanStr);
        }
    }

    @Override
    protected void initListener() {
        switchBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        userNameEdt.addTextChangedListener(this);
    }

    private static final String TAG = "SignInUpActivity";

    private void setEdtEnable(boolean enable) {
        Log.i(TAG, "setEdtEnable: " + enable);
        userNameEdt.setEnabled(enable);
        pwdEdt.setEnabled(enable);
        ensurePwdEdt.setEnabled(enable);
        loginBtn.setEnabled(enable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn: {
                switch (loginBtn.getStatus()) {
                    case LoadingCircleBtn.STATUS_DEFAULT:
                    case LoadingCircleBtn.STATUS_LOAD_FAILED:
                    case LoadingCircleBtn.STATUS_LOAD_SUCCESS:
                        loginBtn.setStatus(LoadingCircleBtn.STATUS_LOADING);
                        setEdtEnable(false);
                        if (isLoginStatus) {
                            mPresenter.signIn(
                                    userNameEdt.getText().toString(),
                                    pwdEdt.getText().toString());
                        } else {
                            mPresenter.signUp(
                                    userNameEdt.getText().toString(),
                                    pwdEdt.getText().toString(),
                                    ensurePwdEdt.getText().toString()
                            );
                        }
                        break;

                }
                break;
            }
            case R.id.login_switch_btn: {
                switchLoginStatus(!isLoginStatus);
                setEdtEnable(true);
                break;
            }
        }
    }

    @Override
    public void onSignInSuccess() {
        tipsTv.setText("登录成功");
        loginBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_SUCCESS);
        setEdtEnable(true);
        if (null == UserInfo.getUser().getMachineId()) {
            Intent intent = new Intent(SignInUpActivity.this, ConnectActivity.class);
            startActivity(intent);
        }
//        finish();
    }

    @Override
    public void onSignInFailed(String reason) {
        setEdtEnable(true);
        tipsTv.setText(reason);
        loginBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_FAILED);
    }

    @Override
    public void onSignUpSuccess() {
        setEdtEnable(true);
        loginBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_SUCCESS);
        showShort("注册成功");
        switchLoginStatus(true);

    }

    @Override
    public void onSignUpFailed(String reason) {
        setEdtEnable(true);
        loginBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_FAILED);
        tipsTv.setText(reason);
    }

    @Override
    public void onUserNameStatus(boolean canUser) {
        if (!canUser) {
            tipsTv.setText("该用户名不可用");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!isLoginStatus) {
            mPresenter.checkUserName(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
