package com.letsbook.letsbook.Model;



public class Response_updateProfile {

    private int code;
    private String data;
    private UpdateProfile userDetails;

    public Response_updateProfile(int code, String data, UpdateProfile userDetails) {
        this.code = code;
        this.data = data;
        this.userDetails = userDetails;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public UpdateProfile getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UpdateProfile userDetails) {
        this.userDetails = userDetails;
    }
}
