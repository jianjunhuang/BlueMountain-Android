package com.jianjunhuang.bluemountain.model.bean;

import java.util.Date;

public class User {

    public static final int ONLINE = 0;
    public static final int OUTLINE = 1;
    public static final int WAITING = 2;
    public static final int GETINT = 3;

    private String userId;
    private String name;
    private int status;
    private float favTemperature;
    private float cupSize;
    private String machineId;
    private Date lastUpdate;

    public User() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getFavTemperature() {
        return favTemperature;
    }

    public void setFavTemperature(float favTemperature) {
        this.favTemperature = favTemperature;
    }

    public float getCupSize() {
        return cupSize;
    }

    public void setCupSize(float cupSize) {
        this.cupSize = cupSize;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "\n\nname = " + name +
                "\nuserId = " + userId +
                "\nstatus = " + status +
                "\nfav temp = " + favTemperature +
                "\ncup size = " + cupSize +
                "\nmachineId = " + machineId +
                "\nlastUpdate = " + lastUpdate;
    }

}
