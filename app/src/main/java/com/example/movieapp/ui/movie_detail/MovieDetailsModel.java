/**
 * @file MovieDetailsModel.java
 * @brief This is model class for details screen, it will handle all business logic.
 * @author Nicolas Escobar Cruz
 * @date 15/04/2018
 */
package com.example.movieapp.ui.movie_detail;

import android.util.Log;

import com.example.movieapp.model.Movie;
import com.example.movieapp.utils.network.ApiConnection;
import com.example.movieapp.utils.network.MovieApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movieapp.utils.Constants.CREDITS;
import static com.example.movieapp.utils.network.ApiConnection.API_KEY;

public class MovieDetailsModel implements IMovieDetailsView.Model {

    private final String TAG = "MovieDetailsModel";

    @Override
    public void getMovieDetails(final OnFinishedListener onFinishedListener, int movieId) {

        MovieApi apiService =
                ApiConnection.getClient().create(MovieApi.class);

        Call<Movie> call = apiService.getMovieDetails(movieId, API_KEY, CREDITS);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Log.d(TAG, "Movie data received: " + movie.toString());
                onFinishedListener.onFinished(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });

    }
}
