package com.jianjunhuang.bluemountain.presenter;

import com.jianjunhuang.bluemountain.contact.ConnectContact;
import com.jianjunhuang.bluemountain.model.ConnectModel;

public class ConnectPresenter implements ConnectContact.Presenter {

    private ConnectContact.View mView;
    private ConnectContact.Model mModel;

    public ConnectPresenter(final ConnectContact.View mView) {
        this.mView = mView;
        mModel = new ConnectModel();
        mModel.setCallback(new ConnectContact.Callback() {
            @Override
            public void onConnectedSuccess() {
                mView.onConnectedSuccess();
            }

            @Override
            public void onConnectedFailed(String reason) {
                mView.onConnectedFailed(reason);
            }
        });
    }

    @Override
    public void connect(String userId, String machineId) {
        if (null == userId || "".equals(userId)) {
            mView.onConnectedFailed("userId is null or equal ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onConnectedFailed("machineId is null or equal ''");
            return;
        }
        mModel.connect(userId, machineId);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
