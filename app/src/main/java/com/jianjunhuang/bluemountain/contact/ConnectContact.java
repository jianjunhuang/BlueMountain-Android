package com.jianjunhuang.bluemountain.contact;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;

public interface ConnectContact {
    public interface View extends IView {
        void onConnectedSuccess();

        void onConnectedFailed(String reason);
    }

    public interface Model extends IModel {
        void connect(String userId, String machineId);

        void setCallback(Callback callback);
    }

    public interface Presenter extends IPresenter {

        void connect(String userId, String machineId);
    }

    public interface Callback {
        void onConnectedSuccess();

        void onConnectedFailed(String reason);
    }
}
