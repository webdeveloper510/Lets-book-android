package com.letsbook.letsbook.View.Activitys.Api;

import com.letsbook.letsbook.Model.BookingDetails;
import com.letsbook.letsbook.Model.Favourites;
import com.letsbook.letsbook.Model.GetFavorite;
import com.letsbook.letsbook.Model.MyOrderResponse;
import com.letsbook.letsbook.Model.ResponseBooking;
import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.Model.ResponseTimeSlot;
import com.letsbook.letsbook.Model.Response_ForgotPassword;
import com.letsbook.letsbook.Model.Response_Global;
import com.letsbook.letsbook.Model.Response_HomeData;
import com.letsbook.letsbook.Model.Response_Login;
import com.letsbook.letsbook.Model.ResponseShopsList;
import com.letsbook.letsbook.Model.Response_updateProfile;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface API_Interface {


    @FormUrlEncoded
    @POST("singnUp")
    Call<Response_Login> hitSignUpApi(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("phone") String phone);

    @FormUrlEncoded
    @POST("login")
    Call<Response_Login> hitLoginApi(@Field("email") String email,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<Response_Global> hitForgotPasswordApi(@Field("email") String email);

    @FormUrlEncoded
    @POST("getProfile")
    Call<Response_ForgotPassword> hitGetProfileApi(@Field("user_id") String user_id,
                                                   @Field("profile_image") String profile_image,
                                                   @Field("name") String name,
                                                   @Field("email") String email,
                                                   @Field("phone") String phone,
                                                   @Field("gender") String gender);

    @Multipart
    @POST("updateProfile")
    Call<Response_updateProfile> hitUpdateProfileApi(@Part("user_id") RequestBody user_id,
                                                     @Part MultipartBody.Part profile,
                                                     @Part("name") RequestBody name,
                                                     @Part("email") RequestBody email,
                                                     @Part("phone") RequestBody phone,
                                                     @Part("gender") RequestBody gender);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<Response_updateProfile> hitUpdatePasswordApi(@Field("old_password") String old_password,
                                                      @Field("new_poasword") String new_poasword,
                                                      @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getServices")
    Call<Response_HomeData> GetServicesApi(@Field("id") String id,
                                           @Field("name") String name,
                                           @Field("categories") String categories);

    @FormUrlEncoded
    @POST("filterServices")
    Call<ResponseShopsList> getShopsList(@Field("id") String id,
                                         @Field("name") String name,
                                         @Field("image") String image,
                                         @Field("description") String description,
                                         @Field("services") String services,
                                         @Field("address") String address,
                                         @Field("lat") String lat,
                                         @Field("lng") String lng,
                                         @Field("opening_time") String opening_time,
                                         @Field("closing_time") String closing_time,
                                         @Field("created_at") String created_at,
                                         @Field("updated_at") String updated_at);



    @FormUrlEncoded
    @POST("filterServices")
    Call<ResponseShopsList> getShopList(@Field("lat") double lat,
                                        @Field("lng") double lng,
                                        @Field("category") int category,
                                        @Field("distance") int distance);


    @FormUrlEncoded
    @POST("getShopDetails")
    Call<ResponseShopDetails> bookAnAppointment(@Field("id") String id,
                                                @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("timeslot")
    Call<ResponseTimeSlot> timeSlot(@Field("id") String id,
                                    @Field("today") String today,
                                    @Field("day") String day,
                                    @Field("time") String time);
    @FormUrlEncoded
    @POST("favourites")
    Call<Favourites> addFavourites(@Field("user_id") String id,
                                   @Field("status") int status,
                                   @Field("id") String time);
    @FormUrlEncoded
    @POST("getfavourites")
    Call<GetFavorite> getFavourites(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getShopDetails")
    Call<ResponseShopDetails.Datum> bookAnAppointments(@Field("id") String id);

    @FormUrlEncoded
    @POST("booking")
    Call<ResponseBooking> conformBooking(@Field("id") String id,
                                         @Field("date") String date,
                                         @Field("time") String time,
                                         @Field("notes") String notes,
                                         @Field("user_id") String user_id,
                                         @Field("service_id") String service_id,
                                         @Field("item_id") String item_id,
                                         @Field("amount") String amount);

   /* @FormUrlEncoded
    @POST("myoder")
    Call<ResponseBooking> myOrder(@Field("id") String id,
                                         @Field("shop_id") String shop_id,
                                         @Field("date") String date,
                                         @Field("time") String time,
                                         @Field("notes") String notes,
                                         @Field("user_id") String user_id,
                                         @Field("service_id") String service_id,
                                         @Field("item_id") String item_id,
                                         @Field("amount") String amount,
                                         @Field("status") String status,
                                         @Field("item_name") String item_name);*/

    @FormUrlEncoded
    @POST("myoder")
    Call<MyOrderResponse> myOrder(@Field("user_id") String user_id);


}
