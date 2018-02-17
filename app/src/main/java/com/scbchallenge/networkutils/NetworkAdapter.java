package com.scbchallenge.networkutils;


import com.scbchallenge.BuildConfig;
import com.scbchallenge.servicegateway.SCBService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkAdapter {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static SCBService service = null;

    public static SCBService getInstance() {
        if (service == null) {
            service = retrofit.create(SCBService.class);
        }
        return service;
    }
}
