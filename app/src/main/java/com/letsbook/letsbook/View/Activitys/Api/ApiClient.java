package com.letsbook.letsbook.View.Activitys.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_Url = "http://letsbookit.co.za/bookit/api/";
    private static ApiClient mInstance;
    private Retrofit retrofit;

    private ApiClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Logging Interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        retrofit = new Retrofit.Builder().baseUrl(BASE_Url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if (mInstance == null){
            mInstance = new ApiClient();
        }
        return mInstance;
    }
    // use to send post request
    public API_Interface getApi(){
        return retrofit.create(API_Interface.class);
    }

    //get OkHttp instance
    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(httpLoggingInterceptor);
        builder.connectTimeout(8500, TimeUnit.SECONDS);
        builder.readTimeout(8500, TimeUnit.SECONDS);
        builder.writeTimeout(8500, TimeUnit.SECONDS);
        return builder.build();
    }
}
