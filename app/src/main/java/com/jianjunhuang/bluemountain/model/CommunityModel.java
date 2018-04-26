package com.jianjunhuang.bluemountain.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.contact.CommunityContact;
import com.jianjunhuang.bluemountain.model.bean.Community;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class CommunityModel implements CommunityContact.Model<Community> {

    private CommunityContact.Callback<Community> mCallback;

    private Gson gson = new Gson();

    @Override
    public void getCommunity(String userId, String machineId) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.GET_COMMUNITY)
                .params("userId", userId)
                .params("machineId", machineId)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onGetFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result<List<Community>> result = gson.fromJson(response,
                                new TypeToken<Result<List<Community>>>() {
                                }.getType());
                        if (result.getStatus() != Result.SUCCESS) {
                            mCallback.onGetFailed(result.getReason());
                        } else {
                            mCallback.onGetSuccess(result.getData());
                        }

                    }
                });
    }

    @Override
    public void sendPosition(String userId, String communityId, int isAgree) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.VOTE_COMMUNITY)
                .params("userId", userId)
                .params("communityId", communityId)
                .params("isAgree", isAgree + "")
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onSendFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result result = gson.fromJson(response, Result.class);
                        if (result.getStatus() == Result.SUCCESS) {
                            mCallback.onSendSuccess();
                        } else {
                            mCallback.onSendFailed(result.getReason());
                        }
                    }
                });
    }

    @Override
    public void setCallback(CommunityContact.Callback<Community> callback) {
        this.mCallback = callback;
    }

    @Override
    public void onDestroy() {

    }
}
