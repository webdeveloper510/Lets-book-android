package com.letsbook.letsbook.Model;

public class BookingDetails {
    private String id, date,time,notes,user_id,service_id,item_id,amount;

    public BookingDetails(String id, String date, String time, String notes, String user_id, String service_id, String item_id, String amount) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.user_id = user_id;
        this.service_id = service_id;
        this.item_id = item_id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
