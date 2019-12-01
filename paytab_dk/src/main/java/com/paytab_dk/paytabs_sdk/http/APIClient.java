package com.paytab_dk.paytabs_sdk.http;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public APIClient() {
    }

    public static Retrofit getClient() {
        OkHttpClient client = (new Builder()).build();
        retrofit = (new retrofit2.Retrofit.Builder()).baseUrl("https://www.paytabs.com").addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit;
    }
}