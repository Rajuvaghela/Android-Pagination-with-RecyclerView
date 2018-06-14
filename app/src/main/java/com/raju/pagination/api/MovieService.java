package com.raju.pagination.api;


import com.raju.pagination.models.AllProductResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MovieService {

    @POST("get_products")
    Call<AllProductResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
                @Query("page") int pageIndex
    );

}
