/**
 * @file ApiConnection.java
 * @brief This class will have all constants related to the networking and retrofit instance
 * @author Nicolas Escobar
 * @date 15/04/2018
 */
package com.example.movieapp.utils.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    public static final String API_KEY = "b270bdb30c87b2fb2b5496ca5ea0e328";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200/";
    public static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780/";


    private ApiConnection() {
    }

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
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
