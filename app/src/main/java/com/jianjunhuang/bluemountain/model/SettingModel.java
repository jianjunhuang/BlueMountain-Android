package com.jianjunhuang.bluemountain.model;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jianjunhuang.bluemountain.application.UrlValue;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.SettingContact;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.Result;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.ResultCallback;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.Call;

public class SettingModel implements SettingContact.Model {

    private SettingContact.Callback mCallback;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        public Date deserialize(JsonElement jsonElement,
                                Type type,
                                JsonDeserializationContext context) throws
                JsonParseException {
            return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
        }
    }).create();

    @Override
    public void getUserInfo() {
        if (null == mCallback) {
            return;
        }
        User user = UserInfo.getUser();
        if (null == user) {
            mCallback.onGetUserInfoFailed("user is null");
            return;
        }
        mCallback.onGetUserInfoSuccess(user);
    }

    @Override
    public void getMachineInfo() {
        if (null == mCallback) {
            return;
        }
        Machine machine = UserInfo.getMachine();
        if (null == machine) {
            mCallback.onGetMachineInfoFailed("machine is null");
            return;
        }
        mCallback.onGetMachineInfoSuccess(machine);
    }

    @Override
    public void updateUserName(String userId, String name) {
        if (null == mCallback) {
            return;
        }

        final User user = UserInfo.getUser();
        if (null == user) {
            mCallback.onUpdateUserNameFailed("user is null");
        }

        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.UPDATE_USER_NAME)
                .params("userId", userId)
                .params("userName", name)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onUpdateUserNameFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Log.i(TAG, "onResponse: " + response);
                        Result<User> result = gson.fromJson(response,
                                new TypeToken<Result<User>>() {
                                }.getType());
                        if (result.getStatus() != Result.SUCCESS) {
                            mCallback.onUpdateUserNameFailed(result.getReason());
                        } else {
                            UserInfo.setUser(result.getData());
                            mCallback.onUpdateUserNameSuccess();
                        }
                    }
                });

    }

    private static final String TAG = "SettingModel";

    @Override
    public void updateUserCupCapacity(String userId, int capacity) {
        if (null == mCallback) {
            return;
        }

        final User user = UserInfo.getUser();
        if (null == user) {
            mCallback.onUpdateUserNameFailed("user is null");
        }

        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.UPDATE_USER_CUP_SIZE)
                .params("userId", userId)
                .params("cupSize", capacity + "")
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onUpdateUserCupCapacityFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result<User> result = gson.fromJson(response,
                                new TypeToken<Result<User>>() {
                                }.getType());
                        if (result.getStatus() != Result.SUCCESS) {
                            mCallback.onUpdateUserCupCapacityFailed(result.getReason());
                        } else {
                            UserInfo.setUser(result.getData());
                            mCallback.onUpdateUserCupCapacitySuccess();
                        }
                    }
                });

    }

    @Override
    public void updateMachineHoldingTemperature(String machineId, int temperature) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.UPDATE_MACHINE_INSULATION)
                .params("machineId", machineId)
                .params("temperature", temperature + "")
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onUpdateMachineTemperatureFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result<Machine> result = gson.fromJson(response,
                                new TypeToken<Result<Machine>>() {
                                }.getType());
                        if (result.getStatus() != Result.SUCCESS) {
                            mCallback.onUpdateMachineTemperatureFailed(result.getReason());
                        } else {
                            UserInfo.setMachine(result.getData());
                            mCallback.onUpdateMachineTemperatureSuccess();
                        }
                    }
                });
    }

    @Override
    public void invite(String machineId, Bitmap logoBmp) {
        if (null == mCallback) {
            return;
        }
        Bitmap bitmap = CodeUtils.createImage(machineId, 400, 400, logoBmp);
        if (null == bitmap) {
            mCallback.onCreateQRFailed("can't create the code");
        } else {
            mCallback.onCreateQRSuccess(bitmap, machineId);
        }
    }

    @Override
    public void disconnect(String userId, String machineId) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.DISCONNECT)
                .params("userId", userId)
                .params("machineId", machineId)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onDisconnectedFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result<User> result = gson.fromJson(response,
                                new TypeToken<Result<User>>() {
                                }.getType());
                        if (result.getStatus() != Result.SUCCESS) {
                            mCallback.onDisconnectedFailed(result.getReason());
                        } else {
                            UserInfo.setUser(result.getData());
                            mCallback.onDisconnectedSuccess();
                        }
                    }
                });
    }

    @Override
    public void setCallback(SettingContact.Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onDestroy() {

    }
}
