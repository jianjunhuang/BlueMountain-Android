package com.jianjunhuang.bluemountain.application;

import android.app.Application;

import com.demo.jianjunhuang.mvptools.integration.BaseApplication;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.https.HttpCode;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BlueMountainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3000L, TimeUnit.MILLISECONDS);
        builder.readTimeout(3000L, TimeUnit.MILLISECONDS);
        OkHttpUtils.initUtils(builder.build());
        HttpCode.initMap(this);
    }
}
