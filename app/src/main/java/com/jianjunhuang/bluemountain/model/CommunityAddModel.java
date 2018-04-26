package com.jianjunhuang.bluemountain.model;

import com.google.gson.Gson;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.contact.CommunityAddContact;
import com.jianjunhuang.bluemountain.model.bean.Community;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;

public class CommunityAddModel implements CommunityAddContact.Model {

    private CommunityAddContact.Callback mCallback;
    private Gson gson = new Gson();

    @Override
    public void addCommunity(String title, String content, String userId, String machineId) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.ADD_COMMUNITY)
                .params("userId", userId)
                .params("machineId", machineId)
                .params("title", title)
                .params("content", content)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onAddFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result result = gson.fromJson(response, Result.class);
                        if (result.getStatus() == Result.SUCCESS) {
                            mCallback.onAddSuccess();
                        } else {
                            mCallback.onAddFailed(result.getReason());
                        }
                    }
                });

    }

    @Override
    public void setCallback(CommunityAddContact.Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onDestroy() {

    }
}
