package com.letsbook.letsbook.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTimeSlot {


    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("time_slot")
        @Expose
        private List<String> timeSlot = null;

        public List<String> getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(List<String> timeSlot) {
            this.timeSlot = timeSlot;
        }

    }
}








