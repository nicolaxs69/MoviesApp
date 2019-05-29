/**
 * @file IMovieTopView.java
 * @brief ViewInterface class, it will have interfaces for model, view and presenter.
 * @author Nicolas Escobar Cruz
 * @date 28/05/2019
 */

package com.example.movieapp.ui.movie_top;

import com.example.movieapp.model.Movie;

import java.util.List;

public interface IMovieTopView {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<Movie> movieArrayList);

            void onFailure(Throwable t);
        }

        void getMovieList(OnFinishedListener onFinishedListener, int pageNo);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Movie> movieArrayList);

        void onResponseFailure(Throwable throwable);

        void onMovieItemClick(int position);

        void showEmptyView();

        void hideEmptyView();

    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();

    }
}
