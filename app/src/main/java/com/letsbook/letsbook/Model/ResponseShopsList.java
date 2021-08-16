package com.letsbook.letsbook.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseShopsList {

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
        @SerializedName("shop_name")
        @Expose
        private String shopName;
        @SerializedName("shop_owner_name")
        @Expose
        private String shopOwnerName;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("shop_owner_img")
        @Expose
        private String shopOwnerImg;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("services")
        @Expose
        private String services;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("opening_time_mon_fri")
        @Expose
        private String openingTimeMonFri;
        @SerializedName("closing_time_mon_fri")
        @Expose
        private String closingTimeMonFri;
        @SerializedName("opening_time_sat_sun")
        @Expose
        private String openingTimeSatSun;
        @SerializedName("closing_time_sat_sun")
        @Expose
        private String closingTimeSatSun;
        @SerializedName("portfolio")
        @Expose
        private String portfolio;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("opening_time")
        @Expose
        private String openingTime;
        @SerializedName("closing_time")
        @Expose
        private String closingTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopOwnerName() {
            return shopOwnerName;
        }

        public void setShopOwnerName(String shopOwnerName) {
            this.shopOwnerName = shopOwnerName;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getShopOwnerImg() {
            return shopOwnerImg;
        }

        public void setShopOwnerImg(String shopOwnerImg) {
            this.shopOwnerImg = shopOwnerImg;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getOpeningTimeMonFri() {
            return openingTimeMonFri;
        }

        public void setOpeningTimeMonFri(String openingTimeMonFri) {
            this.openingTimeMonFri = openingTimeMonFri;
        }

        public String getClosingTimeMonFri() {
            return closingTimeMonFri;
        }

        public void setClosingTimeMonFri(String closingTimeMonFri) {
            this.closingTimeMonFri = closingTimeMonFri;
        }

        public String getOpeningTimeSatSun() {
            return openingTimeSatSun;
        }

        public void setOpeningTimeSatSun(String openingTimeSatSun) {
            this.openingTimeSatSun = openingTimeSatSun;
        }

        public String getClosingTimeSatSun() {
            return closingTimeSatSun;
        }

        public void setClosingTimeSatSun(String closingTimeSatSun) {
            this.closingTimeSatSun = closingTimeSatSun;
        }

        public String getPortfolio() {
            return portfolio;
        }

        public void setPortfolio(String portfolio) {
            this.portfolio = portfolio;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }

        public String getClosingTime() {
            return closingTime;
        }

        public void setClosingTime(String closingTime) {
            this.closingTime = closingTime;
        }

    }
}




