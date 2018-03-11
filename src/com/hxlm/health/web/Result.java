package com.hxlm.health.web;

/**
 * Created by dengyang on 15/6/16.
 */

public class Result {

    private Object data;

    private int status;

    private String message;

    public int getStatus(){ return status; }

    public void setStatus(int status) { this.status = status; }

    public Object getData() { return data; }

    public void setData(Object data) { this.data = data; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}