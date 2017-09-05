package com.example.rbock2.sneakersviewer;

import com.fasterxml.jackson.databind.JsonNode;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit implementation
 *
 * @author rbock2
 */

public interface SneakersService {

    @GET("?filter=marketplace(US)&filter=language(en)&filter=channelId(06cec393-5bd2-4835-bbd5-89c8dbcae1ff)")
    Call<JsonNode> getContent();
}
