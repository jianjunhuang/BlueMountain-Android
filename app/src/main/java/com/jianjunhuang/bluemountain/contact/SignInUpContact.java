package com.jianjunhuang.bluemountain.contact;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;

public interface SignInUpContact {

    public interface View extends IView {
        void onSignInSuccess();

        void onSignInFailed(String reason);

        void onSignUpSuccess();

        void onSignUpFailed(String reason);

        void onUserNameStatus(boolean canUser);
    }

    public interface Model extends IModel {
        void signIn(String userName, String pwd);

        void signUp(String userName, String pwd, String surePwd);

        void checkUserName(String userName);

        void setCallback(Callback callback);
    }

    public interface Presenter extends IPresenter {
        void signIn(String userName, String pwd);

        void signUp(String userName, String pwd, String surePwd);

        void checkUserName(String userName);
    }

    public interface Callback {
        void onSignInSuccess();

        void onSignInFailed(String reason);

        void onSignUpSuccess();

        void onSignUpFailed(String reason);

        void onUserNameStatus(boolean canUser);
    }

}
