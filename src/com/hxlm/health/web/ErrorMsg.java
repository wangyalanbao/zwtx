package com.hxlm.health.web;

/**
 * Created by Administrator on 2018/3/11.
 */
public class ErrorMsg {

    private int code;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
