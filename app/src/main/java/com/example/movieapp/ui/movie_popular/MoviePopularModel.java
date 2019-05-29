/**
 * @file MoviePopularModel.java
 * @brief Model class, it will handle all business logic.
 * @author Nicolas Escobar Cruz
 * @date 28/05/2019
 */

package com.example.movieapp.ui.movie_popular;

import android.util.Log;

import java.util.List;

import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieResponse;
import com.example.movieapp.utils.network.ApiConnection;
import com.example.movieapp.utils.network.MovieApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movieapp.utils.network.ApiConnection.API_KEY;

public class MoviePopularModel implements IMoviePopularView.Model {

    private final String TAG = "MoviePopularModel";


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

        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, pageNo);
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
