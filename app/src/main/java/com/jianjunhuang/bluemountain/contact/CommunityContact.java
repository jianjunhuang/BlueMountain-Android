package com.jianjunhuang.bluemountain.contact;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;

import java.util.List;

public class CommunityContact {

    public interface View<T> extends IView {
        void onGetSuccess(List<T> list);

        void onGetFailed(String reason);

        void onSendSuccess();

        void onSendFailed(String reason);

    }

    public interface Model<T> extends IModel {
        void getCommunity(String userId, String machineId);

        void sendPosition(String userId, String communityId, int isAgree);

        void setCallback(Callback<T> callback);
    }

    public interface Presenter extends IPresenter {
        void getCommunity(String userId, String machineId);

        void sendPosition(String userId, String communityId, int isAgree);
    }

    public interface Callback<T> {
        void onGetSuccess(List<T> list);

        void onGetFailed(String reason);

        void onSendSuccess();

        void onSendFailed(String reason);
    }
}
