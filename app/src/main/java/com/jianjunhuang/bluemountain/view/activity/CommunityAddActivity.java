package com.jianjunhuang.bluemountain.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.CommunityAddContact;
import com.jianjunhuang.bluemountain.presenter.CommunityAddPresenter;

public class CommunityAddActivity extends BaseActivity implements CommunityAddContact.View {

    private EditText titleEdt;
    private EditText contentEdt;
    private FloatingActionButton sendFab;

    private CommunityAddPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.community_add_activity;
    }

    @Override
    protected void initView() {
        mPresenter = new CommunityAddPresenter(this);
        titleEdt = findView(R.id.community_add_title_edt);
        contentEdt = findView(R.id.community_add_content_edt);
        sendFab = findView(R.id.community_add_send_fab);
    }

    @Override
    protected void initListener() {
        sendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addCommunity(titleEdt.getText().toString(),
                        contentEdt.getText().toString(),
                        UserInfo.getUser().getUserId(),
                        UserInfo.getMachine().getMachineId());
            }
        });
    }

    @Override
    public void onAddSuccess() {
        ToastUtils.show("添加成功");
        finish();
    }

    @Override
    public void onAddFailed(String reason) {
        ToastUtils.show(reason);
    }
}
