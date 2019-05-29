/**
 * @file MovieDetailsPresenter.java
 * @brief Presenter  class it will act as an intermediate between views and model
 * @author Nicolas Escobar Cruz
 * @date 15/04/2018
 */

package com.example.movieapp.ui.movie_detail;

import com.example.movieapp.model.Movie;

public class MovieDetailsPresenter implements IMovieDetailsView.Presenter, IMovieDetailsView.Model.OnFinishedListener {

    private IMovieDetailsView.View movieDetailView;
    private IMovieDetailsView.Model movieDetailsModel;

    public MovieDetailsPresenter(IMovieDetailsView.View movieDetailView) {
        this.movieDetailView = movieDetailView;
        this.movieDetailsModel = new MovieDetailsModel();
    }

    @Override
    public void onDestroy() {

        movieDetailView = null;
    }

    @Override
    public void requestMovieData(int movieId) {

        if(movieDetailView != null){
            movieDetailView.showProgress();
        }
        movieDetailsModel.getMovieDetails(this, movieId);
    }

    @Override
    public void onFinished(Movie movie) {

        if(movieDetailView != null){
            movieDetailView.hideProgress();
        }
        movieDetailView.setDataToViews(movie);
    }

    @Override
    public void onFailure(Throwable t) {
        if(movieDetailView != null){
            movieDetailView.hideProgress();
        }
        movieDetailView.onResponseFailure(t);
    }
}
