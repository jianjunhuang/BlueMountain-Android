package com.jianjunhuang.bluemountain.model;

import android.util.Log;

import com.demo.jianjunhuang.mvptools.utils.EncryptionUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.SignInUpContact;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;

public class SignInUpModel implements SignInUpContact.Model {

    private SignInUpContact.Callback mCallback;
    private Gson gson = new Gson();
    private static final String TAG = "SignInUpModel";

    @Override
    public void signIn(String userName, String pwd) {
        if (mCallback == null) {
            return;
        }

        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.LOGIN)
                .params("userName", userName)
                .params("userPwd", EncryptionUtils.getSHA1(pwd))
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onSignInFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result<User> result = gson.fromJson(response,
                                new TypeToken<Result<User>>() {
                                }.getType());
                        Log.i(TAG, "onResponse: response = " + response);
                        Log.i(TAG, "onResponse: result = " + result);
                        Log.i(TAG, "onResponse: " + result.getData());
                        if (result.getStatus() == Result.SUCCESS) {
                            UserInfo.setUser(result.getData());
                            mCallback.onSignInSuccess();
                        } else {
                            mCallback.onSignInFailed(result.getReason());
                        }
                    }
                });
    }

    @Override
    public void signUp(String userName, String pwd, String surePwd) {
        if (mCallback == null) {
            return;
        }

        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.REGISTER)
                .params("name", userName)
                .params("pwd", EncryptionUtils.getSHA1(pwd))
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onSignUpFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Log.i(TAG, "onResponse: response = " + response);
                        Result result = gson.fromJson(response, Result.class);
                        Log.i(TAG, "onResponse: result = " + result);
                        if (result.getStatus() == Result.SUCCESS) {
                            mCallback.onSignUpSuccess();
                        } else {
                            mCallback.onSignUpFailed(result.getReason());
                        }
                    }
                });

    }

    @Override
    public void checkUserName(String userName) {
        if (mCallback == null) {
            return;
        }

        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.CHECK_USERNAME)
                .params("userName", userName)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onUserNameStatus(false);
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result result = gson.fromJson(response, Result.class);
                        Log.i(TAG, "onResponse: response = " + response);
                        Log.i(TAG, "onResponse: result = " + result);
                        if (result.getStatus() == Result.SUCCESS) {
                            mCallback.onUserNameStatus(true);
                        } else {
                            mCallback.onUserNameStatus(false);
                        }
                    }
                });
    }

    @Override
    public void setCallback(SignInUpContact.Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onDestroy() {
        gson = null;
    }
}
