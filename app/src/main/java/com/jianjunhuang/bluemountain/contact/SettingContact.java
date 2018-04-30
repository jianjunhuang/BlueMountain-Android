package com.jianjunhuang.bluemountain.contact;

import android.graphics.Bitmap;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

public interface SettingContact {

    public interface View extends IView {
        void onGetUserInfoSuccess(User user);

        void onGetUserInfoFailed(String reason);

        void onGetMachineInfoSuccess(Machine machine);

        void onGetMachineInfoFailed(String reason);

        void onUpdateUserCupCapacitySuccess();

        void onUpdateUserCupCapacityFailed(String reason);

        void onUpdateUserNameSuccess();

        void onUpdateUserNameFailed(String reason);

        void onUpdateMachineTemperatureSuccess();

        void onUpdateMachineTemperatureFailed(String reason);

        void onCreateQRSuccess(Bitmap bitmap, String machineId);

        void onCreateQRFailed(String reason);

        void onDisconnectedSuccess();

        void onDisconnectedFailed(String reason);
    }

    public interface Model extends IModel {
        void getUserInfo();

        void getMachineInfo();

        void updateUserName(String userId, String name);

        void updateUserCupCapacity(String userId, int capacity);

        void updateMachineHoldingTemperature(String machineId, int temperature);

        void invite(String machineId, Bitmap logoBmp);

        void disconnect(String userId, String machineId);

        void setCallback(Callback callback);
    }

    public interface Presenter extends IPresenter {
        void getUserInfo();

        void getMachineInfo();

        void updateUserName(String userId, String name);

        void updateUserCupCapacity(String userId, int capacity);

        void updateMachineHoldingTemperature(String machineId, int temperature);

        void invite(String machineId, Bitmap logoBmp);

        void disconnect(String userId, String machineId);
    }

    public interface Callback {
        void onGetUserInfoSuccess(User user);

        void onGetUserInfoFailed(String reason);

        void onGetMachineInfoSuccess(Machine machine);

        void onGetMachineInfoFailed(String reason);

        void onUpdateUserCupCapacitySuccess();

        void onUpdateUserCupCapacityFailed(String reason);

        void onUpdateUserNameSuccess();

        void onUpdateUserNameFailed(String reason);

        void onUpdateMachineTemperatureSuccess();

        void onUpdateMachineTemperatureFailed(String reason);

        void onCreateQRSuccess(Bitmap bitmap, String machineId);

        void onCreateQRFailed(String reason);

        void onDisconnectedSuccess();

        void onDisconnectedFailed(String reason);
    }

}
