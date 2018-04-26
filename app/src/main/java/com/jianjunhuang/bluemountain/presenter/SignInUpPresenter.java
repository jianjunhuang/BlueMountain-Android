package com.jianjunhuang.bluemountain.presenter;

import com.jianjunhuang.bluemountain.contact.SignInUpContact;
import com.jianjunhuang.bluemountain.model.SignInUpModel;

public class SignInUpPresenter implements SignInUpContact.Presenter {

    private SignInUpContact.View mView;
    private SignInUpContact.Model mModel;

    public SignInUpPresenter(final SignInUpContact.View mView) {
        this.mView = mView;
        mModel = new SignInUpModel();
        mModel.setCallback(new SignInUpContact.Callback() {
            @Override
            public void onSignInSuccess() {
                mView.onSignInSuccess();
            }

            @Override
            public void onSignInFailed(String reason) {
                mView.onSignInFailed(reason);
            }

            @Override
            public void onSignUpSuccess() {
                mView.onSignUpSuccess();
            }

            @Override
            public void onSignUpFailed(String reason) {
                mView.onSignUpFailed(reason);
            }

            @Override
            public void onUserNameStatus(boolean canUser) {
                mView.onUserNameStatus(canUser);
            }
        });
    }

    @Override
    public void signIn(String userName, String pwd) {
        if (null == userName || "".equals(userName)) {
            mView.onSignInFailed("请输入用户名");
            return;
        }
        if (null == pwd || "".equals(pwd)) {
            mView.onSignInFailed("请输入密码");
            return;
        }
        if (pwd.length() != 8) {
            mView.onSignInFailed("请输入 8位 的正确密码");
            return;
        }
        mModel.signIn(userName, pwd);
    }

    @Override
    public void signUp(String userName, String pwd, String surePwd) {
        if (null == userName || "".equals(userName)) {
            mView.onSignUpFailed("请输入用户名");
            return;
        }
        if (null == pwd || "".equals(pwd) || pwd.length() != 8) {
            mView.onSignUpFailed("请输入 8位 密码");
            return;
        }
        if (null == surePwd || "".equals(surePwd) || surePwd.length() != 8) {
            mView.onSignUpFailed("请再次输入 8位 密码");
            return;
        }
        if (!pwd.equals(surePwd)) {
            mView.onSignUpFailed("两次输入的密码不一致，请重新输入");
            return;
        }
        mModel.signUp(userName, pwd, surePwd);
    }

    @Override
    public void checkUserName(String userName) {
        if (null == userName || "".equals(userName)) {
            mView.onUserNameStatus(false);
            return;
        }
        mModel.checkUserName(userName);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
