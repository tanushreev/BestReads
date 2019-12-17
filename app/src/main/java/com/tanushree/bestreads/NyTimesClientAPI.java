package com.tanushree.bestreads;

import com.tanushree.bestreads.model.JSONData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NyTimesClientAPI
{
    //String BASE_URL = "https://api.nytimes.com/svc/books/v3/lists/";

    @Headers("Content-Type: application/json")
    @GET("{category}.json")
    Call<JSONData> getData(@Path("category") String path, @Query("api-key") String apiKey);
}