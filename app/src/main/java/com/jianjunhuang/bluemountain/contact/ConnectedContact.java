package com.jianjunhuang.bluemountain.contact;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;
import com.jianjunhuang.bluemountain.model.bean.User;

public class ConnectedContact {

    public interface View extends IView {

    }

    public interface Model extends IModel {
        void connectedToMachine(String machineId, User user, String wifiSsid, String wifiPwd);
    }

    public interface Presenter extends IPresenter {

    }


}
