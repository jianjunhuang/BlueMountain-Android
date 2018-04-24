package com.jianjunhuang.bluemountain.application;

import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.google.gson.Gson;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

public class UserInfo {

    private static User user;
    private static Machine machine;

    public static synchronized User getUser() {
        if (user == null) {
            String userJson = SPUtils.instance(Config.SP_USER_INFO_FILE).getInfo(Config.SP_USER_INFO_KEY, "");
            if (userJson.equals("")) {
                return null;
            }
            user = new Gson().fromJson(userJson, User.class);
        }
        return user;
    }

    public static synchronized Machine getMachine() {
        if (machine == null) {
            String machineJson = SPUtils.instance(Config.SP_MACHINE_INFO_FILE).getInfo(Config.SP_MACHINE_INFO_KEY, "");
            if(machineJson.equals("")){
                return null;
            }
            machine = new Gson().fromJson(machineJson,Machine.class);
        }
        return machine;
    }

    public static void setUser(User user) {
        UserInfo.user = user;
    }

    public static void setMachine(Machine machine) {
        UserInfo.machine = machine;
    }
}
