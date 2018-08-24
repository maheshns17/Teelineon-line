package com.example.admin.teelineon_line.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.admin.teelineon_line.logic.P;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 17/02/2017.
 */
public class Api {
    public static Retrofit getRetrofitBuilder(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(IPActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String url=sharedpreferences.getString("url", P.URL);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        return retrofit;

    }
}