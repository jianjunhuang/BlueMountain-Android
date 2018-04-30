package com.jianjunhuang.bluemountain.presenter;

import android.graphics.Bitmap;

import com.jianjunhuang.bluemountain.contact.SettingContact;
import com.jianjunhuang.bluemountain.model.SettingModel;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

public class SettingPresenter implements SettingContact.Presenter {

    private SettingContact.View mView;
    private SettingContact.Model mModel;

    public SettingPresenter(final SettingContact.View mView) {
        this.mView = mView;
        mModel = new SettingModel();
        mModel.setCallback(new SettingContact.Callback() {
            @Override
            public void onGetUserInfoSuccess(User user) {
                mView.onGetUserInfoSuccess(user);
            }

            @Override
            public void onGetUserInfoFailed(String reason) {
                mView.onGetUserInfoFailed(reason);
            }

            @Override
            public void onGetMachineInfoSuccess(Machine machine) {
                mView.onGetMachineInfoSuccess(machine);
            }

            @Override
            public void onGetMachineInfoFailed(String reason) {
                mView.onGetMachineInfoFailed(reason);
            }

            @Override
            public void onUpdateUserCupCapacitySuccess() {
                mView.onUpdateUserCupCapacitySuccess();
            }

            @Override
            public void onUpdateUserCupCapacityFailed(String reason) {
                mView.onUpdateUserCupCapacityFailed(reason);
            }

            @Override
            public void onUpdateUserNameSuccess() {
                mView.onUpdateUserNameSuccess();
            }

            @Override
            public void onUpdateUserNameFailed(String reason) {
                mView.onUpdateUserNameFailed(reason);
            }

            @Override
            public void onUpdateMachineTemperatureSuccess() {
                mView.onUpdateMachineTemperatureSuccess();
            }

            @Override
            public void onUpdateMachineTemperatureFailed(String reason) {
                mView.onUpdateMachineTemperatureFailed(reason);
            }

            @Override
            public void onCreateQRSuccess(Bitmap bitmap, String machineId) {
                mView.onCreateQRSuccess(bitmap, machineId);
            }

            @Override
            public void onCreateQRFailed(String reason) {
                mView.onCreateQRFailed(reason);
            }

            @Override
            public void onDisconnectedSuccess() {
                mView.onDisconnectedSuccess();
            }

            @Override
            public void onDisconnectedFailed(String reason) {
                mView.onDisconnectedFailed(reason);
            }
        });
    }

    @Override
    public void getUserInfo() {
        mModel.getUserInfo();
    }

    @Override
    public void getMachineInfo() {
        mModel.getMachineInfo();
    }

    @Override
    public void updateUserName(String userId, String name) {
        if (null == userId || "".equals(userId)) {
            mView.onUpdateUserNameFailed("userId is null or equals ''");
            return;
        }
        if (null == name || "".equals(name)) {
            mView.onUpdateUserNameFailed("name is null or equals ''");
            return;
        }
        mModel.updateUserName(userId, name);
    }

    @Override
    public void updateUserCupCapacity(String userId, int capacity) {
        if (null == userId || "".equals(userId)) {
            mView.onUpdateUserCupCapacityFailed("userId is null or equals ''");
            return;
        }
        mModel.updateUserCupCapacity(userId, capacity);
    }

    @Override
    public void updateMachineHoldingTemperature(String machineId, int temperature) {
        if (null == machineId || "".equals(machineId)) {
            mView.onUpdateMachineTemperatureFailed("machineId is null or equals ''");
            return;
        }
        mModel.updateMachineHoldingTemperature(machineId, temperature);
    }

    @Override
    public void invite(String machineId, Bitmap logoBmp) {
        if (null == machineId || "".equals(machineId)) {
            mView.onCreateQRFailed("machineId is null or equals ''");
            return;
        }
        mModel.invite(machineId, logoBmp);
    }

    @Override
    public void disconnect(String userId, String machineId) {
        if (null == userId || "".equals(userId)) {
            mView.onDisconnectedFailed("userId is null or equals ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onDisconnectedFailed("machineId is null or equals ''");
            return;
        }
        mModel.disconnect(userId, machineId);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
