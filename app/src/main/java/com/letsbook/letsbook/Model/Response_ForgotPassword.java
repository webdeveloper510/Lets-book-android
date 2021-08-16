package com.letsbook.letsbook.Model;

public class    Response_ForgotPassword {

    private int code;
    private Model_Login data;

    public Response_ForgotPassword(int code, Model_Login data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Model_Login getData() {
        return data;
    }

    public void setData(Model_Login data) {
        this.data = data;
    }
}
