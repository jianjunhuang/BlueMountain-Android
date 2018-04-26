package com.jianjunhuang.bluemountain.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.ConnectContact;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;

public class ConnectModel implements ConnectContact.Model {

    private ConnectContact.Callback mCallback;
    private Gson gson = new Gson();

    @Override
    public void connect(String userId, String machineId) {
        if (mCallback == null) {
            return;
        }

        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.CONNECT)
                .params("userId", userId)
                .params("machineId", machineId)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onConnectedFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result<Machine> result = gson.fromJson(response, new TypeToken<Result<Machine>>() {
                        }.getType());
                        if (result.getStatus() == Result.SUCCESS) {
                            UserInfo.setMachine(result.getData());
                            mCallback.onConnectedSuccess();
                        } else {
                            mCallback.onConnectedFailed(result.getReason());
                        }
                    }
                });
    }

    @Override
    public void setCallback(ConnectContact.Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onDestroy() {
        mCallback = null;
        gson = null;
    }
}
