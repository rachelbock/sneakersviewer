package com.example.rbock2.sneakersviewer;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Retrofit initialization
 *
 * @author rbock2
 */

public class ApiManager {
    private static final String API_URL = "https://api.nike.com/product_feed/threads/v2/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private static final SneakersService sneakersService = retrofit.create(SneakersService.class);
    public static SneakersService getSneakersService() {
        return sneakersService;
    }
}
