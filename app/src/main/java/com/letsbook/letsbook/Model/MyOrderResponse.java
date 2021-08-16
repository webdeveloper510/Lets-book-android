package com.letsbook.letsbook.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("shop_id")
        @Expose
        private String shopId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("notes")
        @Expose
        private String notes;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("item_id")
        @Expose
        private String itemId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("shop_address")
        @Expose
        private String shopAddress;
        @SerializedName("item_bio")
        @Expose
        private String itemBio;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getItemBio() {
            return itemBio;
        }

        public void setItemBio(String itemBio) {
            this.itemBio = itemBio;
        }

    }
}



