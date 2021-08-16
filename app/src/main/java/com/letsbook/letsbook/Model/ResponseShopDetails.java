package com.letsbook.letsbook.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseShopDetails {

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


    //__________________________________________________________Datum___________________________________________________


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
        @SerializedName("status")
        @Expose
        private int status;
        @SerializedName("portfolio_image")
        @Expose
        private List<String> portfolioImage = null;
        @SerializedName("shop_categories")
        @Expose
        private List<ShopCategory> shopCategories = null;
        @SerializedName("review_details_main")
        @Expose
        private List<ReviewDetailsMain> reviewDetailsMain = null;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<String> getPortfolioImage() {
            return portfolioImage;
        }

        public void setPortfolioImage(List<String> portfolioImage) {
            this.portfolioImage = portfolioImage;
        }

        public List<ShopCategory> getShopCategories() {
            return shopCategories;
        }

        public void setShopCategories(List<ShopCategory> shopCategories) {
            this.shopCategories = shopCategories;
        }

        public List<ReviewDetailsMain> getReviewDetailsMain() {
            return reviewDetailsMain;
        }

        public void setReviewDetailsMain(List<ReviewDetailsMain> reviewDetailsMain) {
            this.reviewDetailsMain = reviewDetailsMain;
        }

    }
    //_______________________ReviewDetailsMain____________________________________________________________________________

    public class ReviewDetailsMain {

        @SerializedName("review_title")
        @Expose
        private String reviewTitle;
        @SerializedName("review_desc")
        @Expose
        private String reviewDesc;
        @SerializedName("rating")
        @Expose
        private String rating;

        @SerializedName("user_id")
        @Expose
        private String userId;

        @SerializedName("bio")
        @Expose
        private String bio;

        public String getReviewTitle() {
            return reviewTitle;
        }

        public void setReviewTitle(String reviewTitle) {
            this.reviewTitle = reviewTitle;
        }

        public String getReviewDesc() {
            return reviewDesc;
        }

        public void setReviewDesc(String reviewDesc) {
            this.reviewDesc = reviewDesc;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

    }

    //______________________________________ShopCategory_______________________________________________________________________

    public class ShopCategory {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("shop_id")
        @Expose
        private String shopId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_image")
        @Expose
        private String categoryImage;

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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

    }

}








