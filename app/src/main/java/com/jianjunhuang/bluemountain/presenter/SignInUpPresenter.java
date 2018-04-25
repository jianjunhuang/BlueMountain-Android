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
        mModel.signIn(userName, pwd);
    }

    @Override
    public void signUp(String userName, String pwd, String surePwd) {
        mModel.signUp(userName, pwd, surePwd);
    }

    @Override
    public void checkUserName(String userName) {
        mModel.checkUserName(userName);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
