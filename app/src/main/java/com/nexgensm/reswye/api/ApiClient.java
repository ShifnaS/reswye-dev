package com.nexgensm.reswye.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
   // public static final String BASE_URL = "http://localhost:3000/reswy/";
    public static final String BASE_URL = "http://192.168.0.3:3000/reswy/";
    public static final String BASE_URL_IMG = "http://192.168.0.3:3000/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
