package com.jianjunhuang.bluemountain.contact;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

import java.util.List;

public interface CoffeeContact {

    public interface View extends IView {
        void onGetUsersSuccess(List<User> users);

        void onGetUsersFailed(String reason);

        void onGetMachineSuccess(Machine machine);

        void onGetMachineFailed(String reason);

        void onOrderCoffeeSuccess();

        void onOrderCoffeeFailed(String reason);

        void onCoffeeFinish();

    }

    public interface Model extends IModel {
        void getUsers(String userId, String machineId);

        void getMachine(String machineId);

        void connectByWebSocket(String userId, String machineId);

        void orderCoffee(String machineId, String userId);

        void setCallback(Callback mCallback);
    }

    public interface Presenter extends IPresenter {
        void getUsers(String userId, String machineId);

        void getMachine(String machineId);

        void connectByWebSocket(String userId, String machineId);

        void orderCoffee(String machineId, String userId);
    }

    public interface Callback {
        void onGetUsersSuccess(List<User> users);

        void onGetUsersFailed(String reason);

        void onGetMachineSuccess(Machine machine);

        void onGetMachineFailed(String reason);

        void onOrderCoffeeSuccess();

        void onOrderCoffeeFailed(String reason);

        void onCoffeeFinish();
    }

}
