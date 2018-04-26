package com.jianjunhuang.bluemountain.model.bean;

import java.util.Date;

public class Machine {
    private String machineId;
    private int status;
    private float temperature;
    private int level;
    private boolean isConnected;
    private float insulation;
    private Date lastUpdate;

    public static final int STATUS_MAKING_COFFEE = 0;
    public static final int STATUS_KEEP_WARMING = 1;
    public static final int STATUS_STANDBY = 2;
    public static final int STATUS_DISCONNECTED = 3;
    public static final int STATUS_NO_WATER = 4;

    public Machine() {
        super();
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public float getInsulation() {
        return insulation;
    }

    public void setInsulation(float insulation) {
        this.insulation = insulation;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "\n\nmachineId = " + machineId +
                "\nstatus = " + status +
                "\ntemperature = " + temperature +
                "\nlevel=" + level +
                "\nisConnected=" + isConnected +
                "\ninsulation=" + insulation +
                "\nlastUpdate=" + lastUpdate;
    }

    @Override
    public int hashCode() {
        return machineId.hashCode();
    }
}
