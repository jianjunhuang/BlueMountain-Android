package com.jianjunhuang.bluemountain.presenter;

import com.jianjunhuang.bluemountain.contact.CoffeeContact;
import com.jianjunhuang.bluemountain.model.CoffeeModel;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

import java.util.List;

public class CoffeePresenter implements CoffeeContact.Presenter {

    private CoffeeContact.View mView;
    private CoffeeContact.Model mModel;

    public CoffeePresenter(final CoffeeContact.View mView) {
        this.mView = mView;
        mModel = new CoffeeModel();
        mModel.setCallback(new CoffeeContact.Callback() {
            @Override
            public void onGetUsersSuccess(List<User> users) {
                mView.onGetUsersSuccess(users);
            }

            @Override
            public void onGetUsersFailed(String reason) {
                mView.onGetUsersFailed(reason);
            }

            @Override
            public void onGetMachineSuccess(Machine machine) {
                mView.onGetMachineSuccess(machine);
            }

            @Override
            public void onGetMachineFailed(String reason) {
                mView.onGetMachineFailed(reason);
            }

            @Override
            public void onOrderCoffeeSuccess() {
                mView.onOrderCoffeeSuccess();
            }

            @Override
            public void onOrderCoffeeFailed(String reason) {
                mView.onOrderCoffeeFailed(reason);
            }

            @Override
            public void onCoffeeFinish() {
                mView.onCoffeeFinish();
            }
        });
    }

    @Override
    public void getUsers(String userId, String machineId) {
        if (null == userId || "".equals(userId)) {
            mView.onGetUsersFailed("userId is null or equals ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onGetUsersFailed("machineId is null or equals ''");
            return;
        }
        mModel.getUsers(userId, machineId);
    }

    @Override
    public void getMachine(String machineId) {
        if (null == machineId || "".equals(machineId)) {
            mView.onGetUsersFailed("machineId is null or equals ''");
            return;
        }
        mModel.getMachine(machineId);
    }

    @Override
    public void connectByWebSocket(String userId, String machineId) {
        if (null == userId || "".equals(userId)) {
            mView.onGetUsersFailed("userId is null or equals ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onGetUsersFailed("machineId is null or equals ''");
            return;
        }
        mModel.connectByWebSocket(userId, machineId);
    }

    @Override
    public void orderCoffee(String machineId, String userId) {
        if (null == userId || "".equals(userId)) {
            mView.onOrderCoffeeFailed("userId is null or equals ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onOrderCoffeeFailed("machineId is null or equals ''");
            return;
        }
        mModel.orderCoffee(machineId, userId);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
