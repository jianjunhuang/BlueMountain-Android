package com.jianjunhuang.bluemountain.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.contact.CommunityContact;
import com.jianjunhuang.bluemountain.model.bean.Community;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class CommunityModel implements CommunityContact.Model<Community> {

    private CommunityContact.Callback<Community> mCallback;

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
                        Log.i(TAG, "onResponse: " + response);
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("status") != Result.SUCCESS) {
                            mCallback.onGetFailed(jsonObject.getString("reason"));
                        } else {
                            List<Community> list = gson.fromJson(
                                    jsonObject.getString("data"),
                                    new TypeToken<List<Community>>() {
                                    }.getType());
                            mCallback.onGetSuccess(list);
                        }
                    }
                });
    }

    private static final String TAG = "CommunityModel";

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
