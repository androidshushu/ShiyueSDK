package com.shiyue.mhxy.user;

/**
 * Created by Administrator on 2017/8/16.
 */

public class RealNameConfimMessage {
    private Boolean result;
    private int code;
    private String message;

    @Override
    public String toString() {
        return "RealNameConfimMessage{" +
                "result='" + result + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
