package com.letsbook.letsbook.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFavorite {

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
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("bio")
        @Expose
        private String bio;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

    }
}




