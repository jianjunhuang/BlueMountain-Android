package com.jianjunhuang.bluemountain.model.bean;

public class Result<T> {
    public final static int SUCCESS = 0;
    public final static int PARAMETER_LOST = 1;
    public final static int PARAMETER_ERR = 2;
    public final static int USER_NOT_FOUND = 3;
    public final static int MACHINE_NOT_FOUND = 4;
    public final static int SERVER_ERR = 5;
    public final static int FAILED = -1;

    private int status;
    private String reason;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "status=" + status +
                "\nreason=" + reason;
    }
}
