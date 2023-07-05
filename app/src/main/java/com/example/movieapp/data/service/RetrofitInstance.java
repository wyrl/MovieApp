package com.example.movieapp.data.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public final static MovieAPI api;
    private final static String baseUrl = "http://192.168.1.4:8080";
    static {
        api = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieAPI.class);
    }
}
