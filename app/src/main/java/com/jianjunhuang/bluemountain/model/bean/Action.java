package com.jianjunhuang.bluemountain.model.bean;

public class Action {

    public static final int UPDATE_USERS = 0;
    public static final int UPDATE_MACHINE = 1;
    public static final int GET_COFFEE = 4;

    private int action;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
