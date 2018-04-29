package com.jianjunhuang.bluemountain.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.contact.CoffeeContact;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class CoffeeModel implements CoffeeContact.Model {

    private CoffeeContact.Callback mCallback;
    private Gson gson = new GsonBuilder().
            registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement jsonElement,
                                        Type type,
                                        JsonDeserializationContext context) throws
                        JsonParseException {
                    return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                }
            }).create();

    @Override
    public void getUsers(String userId, String machineId) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.GET_ALL_USERS)
                .params("userId", userId)
                .params("machineId", machineId)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onGetUsersFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.i(TAG, "users onResponse: " + response);
                        if (jsonObject.getInt("status") == Result.SUCCESS) {
                            List<User> list = gson.fromJson(jsonObject.getString("data"),
                                    new TypeToken<List<User>>() {
                                    }.getType());
                            mCallback.onGetUsersSuccess(list);
                        } else {
                            mCallback.onGetUsersFailed(jsonObject.getString("reason"));
                        }
                    }
                });
    }

    private static final String TAG = "CoffeeModel";

    @Override
    public void getMachine(final String machineId) {
        if (null == mCallback) {
            return;
        }
        Log.i(TAG, "getMachine: " + machineId);
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.GET_MACHINE)
                .params("machineId", machineId)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onGetMachineFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Log.i(TAG, "machine onResponse: " + response);
                        Result<Machine> result = gson.fromJson(response,
                                new TypeToken<Result<Machine>>() {
                                }.getType());
                        if (result.getStatus() != Result.SUCCESS) {
                            mCallback.onGetMachineFailed(result.getReason());
                        } else {
                            mCallback.onGetMachineSuccess(result.getData());
                        }
                    }
                });
    }

    @Override
    public void connectByWebSocket(String userId, String machineId) {

    }

    @Override
    public void setCallback(CoffeeContact.Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onDestroy() {

    }
}
