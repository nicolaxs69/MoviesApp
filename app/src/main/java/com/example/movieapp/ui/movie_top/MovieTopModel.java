/**
 * @file MovieTopModel.java
 * @brief Model class for list screen, it will handle all business logic.
 * @author Nicolas Escobar Cruz
 * @date 28/05/2019
 */

package com.example.movieapp.ui.movie_top;

import android.util.Log;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieResponse;
import com.example.movieapp.ui.movie_top.IMovieTopView;
import com.example.movieapp.utils.network.ApiConnection;
import com.example.movieapp.utils.network.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movieapp.utils.network.ApiConnection.API_KEY;

public class MovieTopModel implements IMovieTopView.Model {

    private final String TAG = "MovieTopModel";


    /**
     * This function will fetch movies data
     *
     * @param onFinishedListener
     * @param pageNo             : Which page to load.
     */
    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener, int pageNo) {

        MovieApi apiService =
                ApiConnection.getClient().create(MovieApi.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY, pageNo);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

}
