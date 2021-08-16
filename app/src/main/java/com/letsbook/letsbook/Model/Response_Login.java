package com.letsbook.letsbook.Model;

public class Response_Login {

    private int code;
    private String msg;
    private Model_Login data;

    public Response_Login(int code, String msg, Model_Login data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Model_Login getData() {
        return data;
    }

    public void setData(Model_Login data) {
        this.data = data;
    }
}
