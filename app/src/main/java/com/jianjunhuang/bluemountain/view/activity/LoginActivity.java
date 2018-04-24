package com.jianjunhuang.bluemountain.view.activity;

import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.jianjunhuang.bluemountain.R;
import com.jinjunhuang.loadingcirclebtn.LoadingCircleBtn;

public class LoginActivity extends BaseActivity {

    private TextInputEditText userNameEdt;
    private TextInputEditText pwdEdt;
    private TextInputEditText ensurePwdEdt;
    private LoadingCircleBtn loginBtn;
    private Button switchBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        userNameEdt = findView(R.id.login_user_name_edt);
        pwdEdt = findView(R.id.login_pwd_edt);
        ensurePwdEdt = findView(R.id.login_ensure_pwd_edt);
        switchBtn = findView(R.id.login_switch_btn);
    }

    @Override
    protected void initListener() {

    }
}
