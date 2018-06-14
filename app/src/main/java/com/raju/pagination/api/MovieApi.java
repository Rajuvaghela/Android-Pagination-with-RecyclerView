package com.raju.pagination.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieApi {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.awanmart.com.pk/?webservice=1&vootouchservice=")
                    .build();
        }
        return retrofit;
    }

}
