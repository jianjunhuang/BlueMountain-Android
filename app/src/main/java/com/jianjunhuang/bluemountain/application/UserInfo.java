package com.jianjunhuang.bluemountain.application;

import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.google.gson.Gson;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

public class UserInfo {

    private static User user;
    private static Machine machine;
    private static final Object lock = new Object();

    public static User getUser() {
        synchronized (lock) {
            if (user == null) {
                String userJson = SPUtils.instance(Config.SP_USER_INFO_FILE).getInfo(Config.SP_USER_INFO_KEY, "");
                if (userJson.equals("")) {
                    return null;
                }
                user = new Gson().fromJson(userJson, User.class);
            }
            return user;
        }
    }

    public static Machine getMachine() {
        synchronized (lock) {
            if (machine == null) {
                String machineJson = SPUtils.instance(Config.SP_MACHINE_INFO_FILE).getInfo(Config.SP_MACHINE_INFO_KEY, "");
                if (machineJson.equals("")) {
                    return null;
                }
                machine = new Gson().fromJson(machineJson, Machine.class);
            }
            return machine;
        }
    }

    public static void setUser(User user) {
        synchronized (lock) {
            UserInfo.user = user;
            if (null != user) {
                Gson gson = new Gson();
                SPUtils.instance(Config.SP_USER_INFO_FILE)
                        .store(Config.SP_USER_INFO_KEY, gson.toJson(user));
            }
        }
    }

    public static void setMachine(Machine machine) {
        synchronized (lock) {
            UserInfo.machine = machine;
            if (null != machine) {
                Gson gson = new Gson();
                SPUtils.instance(Config.SP_MACHINE_INFO_FILE)
                        .store(Config.SP_MACHINE_INFO_KEY, gson.toJson(machine));
            }
        }
    }
}
