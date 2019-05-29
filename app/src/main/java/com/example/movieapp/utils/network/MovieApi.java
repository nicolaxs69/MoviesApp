/**
 * @file MovieApi.java
 * @brief This is the api interface class, it will contain all the Api call references
 * @author Nicolas Escobar
 * @date 28/05/2019
 */
package com.example.movieapp.utils.network;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("append_to_response") String credits);


}
