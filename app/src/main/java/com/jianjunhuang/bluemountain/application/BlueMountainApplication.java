package com.jianjunhuang.bluemountain.application;

import android.app.Application;

import com.demo.jianjunhuang.mvptools.integration.BaseApplication;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class BlueMountainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
