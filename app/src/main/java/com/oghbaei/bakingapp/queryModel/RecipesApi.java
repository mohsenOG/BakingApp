package com.oghbaei.bakingapp.queryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Mohsen on 18.04.2018.
 *
 */

public interface RecipesApi {

    String API_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";

    @GET("59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipes();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
