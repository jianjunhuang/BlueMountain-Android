package com.jianjunhuang.bluemountain.model;

import android.support.annotation.Nullable;
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
import com.jianjunhuang.bluemountain.model.bean.Action;
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
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

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
    public void connectByWebSocket(final String userId, final String machineId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("ws://10.11.3.213:8080/websocket/socketServer")
                .header("machineId", machineId)
                .header("type", "phone")
                .header("userId", userId)
                .build();
        final WebSocket webSocket = client
                .newWebSocket(request, new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                        webSocket.send("open");
                        Log.i(TAG, "onOpen: ");
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        super.onMessage(webSocket, text);
                        Log.i(TAG, "onMessage: " + text);
                        Action action = gson.fromJson(text, Action.class);
                        switch (action.getAction()) {
                            case Action.GET_COFFEE: {
                                Log.i(TAG, "onMessage: get coffee");
                                mCallback.onCoffeeFinish();
                                break;
                            }
                            case Action.UPDATE_MACHINE: {
                                Log.i(TAG, "onMessage: update machine");
                                getMachine(machineId);
                                break;
                            }
                            case Action.UPDATE_USERS: {
                                Log.i(TAG, "onMessage: update users");
                                getUsers(userId, machineId);
                                break;
                            }
                            case Action.NO_WATER: {
                                Log.i(TAG, "onMessage: machine no water");
                                mCallback.onMachineNoWater();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, ByteString bytes) {
                        super.onMessage(webSocket, bytes);
                        Log.i(TAG, "onMessage: ");
                    }

                    @Override
                    public void onClosing(WebSocket webSocket, int code, String reason) {
                        super.onClosing(webSocket, code, reason);
                        Log.i(TAG, "onClosing: ");
                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        super.onClosed(webSocket, code, reason);
                        Log.i(TAG, "onClosed: ");
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                        super.onFailure(webSocket, t, response);
                        Log.i(TAG, "onFailure: ");
                    }
                });
        client.dispatcher()
                .executorService()
                .shutdown();
        //heart beat
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                webSocket.send(userId);
            }
        };
        timer.schedule(timerTask, 0, 30000);
    }

    @Override
    public void orderCoffee(String machineId, String userId) {
        if (null == mCallback) {
            return;
        }
        OkHttpUtils.getInstance()
                .postJsonAsy()
                .baseURL(UrlValue.ORDER_COFFEE)
                .params("machineId", machineId)
                .params("userId", userId)
                .build()
                .execute(new ResultCallback() {
                    @Override
                    public void onError(Call call, int code, Exception e) {
                        mCallback.onOrderCoffeeFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int code) throws IOException, JSONException {
                        Result result = gson.fromJson(response, Result.class);
                        if (result.getStatus() == Result.SUCCESS) {
                            mCallback.onOrderCoffeeSuccess();
                        } else {
                            mCallback.onOrderCoffeeFailed(result.getReason());
                        }
                    }
                });
    }

    @Override
    public void setCallback(CoffeeContact.Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onDestroy() {

    }
}
