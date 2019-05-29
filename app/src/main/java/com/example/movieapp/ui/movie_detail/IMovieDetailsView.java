/**
 * @file IMovieDetailsView.java
 * @brief View interface class for Movie details MVP
 * @author Nicolas
 * @date 29/05/2019
 */
package com.example.movieapp.ui.movie_detail;

import com.example.movieapp.model.Movie;

public interface IMovieDetailsView {

    interface Model {

        interface OnFinishedListener {
            void onFinished(Movie movie);

            void onFailure(Throwable t);
        }
        void getMovieDetails(OnFinishedListener onFinishedListener, int movieId);
    }

    interface View {

        void showProgress();
        void hideProgress();
        void setDataToViews(Movie movie);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void requestMovieData(int movieId);
    }
}
