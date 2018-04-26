package com.jianjunhuang.bluemountain.contact;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;

public class CommunityAddContact {

    public interface View extends IView {
        void onAddSuccess();

        void onAddFailed(String reason);
    }

    public interface Model extends IModel {
        void addCommunity(String title, String content, String userId, String machineId);

        void setCallback(Callback mCallback);
    }

    public interface Presenter extends IPresenter {
        void addCommunity(String title, String content, String userId, String machineId);
    }

    public interface Callback {
        void onAddSuccess();

        void onAddFailed(String reason);
    }

}
