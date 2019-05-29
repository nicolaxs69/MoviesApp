/**
 * @file MovieUpcomingPresenter.java
 * @brief Presentation class, it handles the communication between list view and list model
 * @author Nicolas Escobar Cruz
 * @date 28/05/2018
 */

package com.example.movieapp.ui.movie_upcoming;

import com.example.movieapp.model.Movie;


import java.util.List;

public class MovieUpcomingPresenter implements IMovieUpcomingView.Presenter, IMovieUpcomingView.Model.OnFinishedListener {

    private IMovieUpcomingView.View movieListView;

    private IMovieUpcomingView.Model movieListModel;

    public MovieUpcomingPresenter(IMovieUpcomingView.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new MovieUpcomingModel();
    }

    @Override
    public void onDestroy() {
        this.movieListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {

        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getMovieList(this, pageNo);
    }

    @Override
    public void requestDataFromServer() {

        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getMovieList(this, 1);
    }

    @Override
    public void onFinished(List<Movie> movieArrayList) {
        movieListView.setDataToRecyclerView(movieArrayList);
        if (movieListView != null) {
            movieListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {

        movieListView.onResponseFailure(t);
        if (movieListView != null) {
            movieListView.hideProgress();
        }
    }
}
