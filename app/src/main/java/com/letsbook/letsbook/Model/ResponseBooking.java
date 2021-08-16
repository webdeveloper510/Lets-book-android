package com.letsbook.letsbook.Model;

public class ResponseBooking {
    int code;
    String msg;
    BookingDetails data;

    public ResponseBooking(int code, String msg, BookingDetails data) {
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

    public BookingDetails getData() {
        return data;
    }

    public void setData(BookingDetails data) {
        this.data = data;
    }
}
