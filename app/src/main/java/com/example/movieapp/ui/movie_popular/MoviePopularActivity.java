/**
 * @file MoviePopularActivity.java
 * @brief Activity responsible for showing all the movies data in the list format.
 * @author Nicolas Escobar Cruz
 * @date 28/05/2019
 */

package com.example.movieapp.ui.movie_popular;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.movieapp.R;
import com.example.movieapp.ui.movie_detail.MovieDetailsActivity;
import com.example.movieapp.ui.movie_popular.adapter.MoviesPopularAdapter;
import com.example.movieapp.model.Movie;
import com.example.movieapp.ui.movie_top.MovieTopActivity;
import com.example.movieapp.ui.movie_upcoming.MovieUpcomingActivity;
import com.example.movieapp.utils.GridItemDecorator;


import static com.example.movieapp.utils.Constants.KEY_MOVIE_ID;
import static com.example.movieapp.utils.GridItemDecorator.dpToPx;

public class MoviePopularActivity extends AppCompatActivity implements IMoviePopularView.View {

    private static final String TAG = "MovieTopActivity";

    private List<Movie> moviesList;
    private MoviePopularPresenter moviePopularPresenter;
    private MoviesPopularAdapter moviesPopularAdapter;

    private TextView tvEmptyView;

    private int pageNo = 1;
    private int thresholdLimit = 8;
    private int previousTotal = 0;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private RecyclerView rvMovieList;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar pgLoading;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_popular);
        getSupportActionBar().setTitle(getString(R.string.popular_movies));

        initUI();
        setListeners();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.popular_movies);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initializing presenter
        moviePopularPresenter = new MoviePopularPresenter(this);
        moviePopularPresenter.requestDataFromServer();
    }

    /**
     * This method will initialize the UI components
     */
    private void initUI() {

        rvMovieList = findViewById(R.id.rv_movie_list);

        moviesList = new ArrayList<>();
        moviesPopularAdapter = new MoviesPopularAdapter(this, moviesList);
        gridLayoutManager = new GridLayoutManager(this, 2);
        rvMovieList.setLayoutManager(gridLayoutManager);
        rvMovieList.addItemDecoration(new GridItemDecorator(2, dpToPx(this, 10), true));
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(moviesPopularAdapter);

        pgLoading = findViewById(R.id.pb_loading);
        tvEmptyView = findViewById(R.id.tv_empty_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                moviesPopularAdapter.setFilterParameter(newText);
                moviesPopularAdapter.getFilter().filter("");

                return false;
            }
        });
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.popular_movies:
                    return true;
                case R.id.top_rated_movies:
                    Intent myIntent = new Intent(MoviePopularActivity.this, MovieTopActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    MoviePopularActivity.this.startActivity(myIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.upcoming_movies:
                    Intent myIntent2 = new Intent(MoviePopularActivity.this, MovieUpcomingActivity.class);
                    myIntent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    MoviePopularActivity.this.startActivity(myIntent2);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
            }
            return false;
        }
    };

    /**
     * This function will contain listeners for all views.
     */
    private void setListeners() {

        rvMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvMovieList.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                //Infinite scroll
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + thresholdLimit)) {
                    moviePopularPresenter.getMoreData(pageNo);
                    loading = true;
                }

            }
        });

    }

    @Override
    public void showProgress() {

        pgLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        pgLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<Movie> movieArrayList) {

        moviesList.addAll(movieArrayList);
        moviesPopularAdapter.notifyDataSetChanged();

        // This will help us to fetch data from next page no.
        pageNo++;
    }


    @Override
    public void onResponseFailure(Throwable throwable) {

        Log.e(TAG, throwable.getMessage());
        Toast.makeText(this, getString(R.string.communication_error), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moviePopularPresenter.onDestroy();
    }

    @Override
    public void onMovieItemClick(int position) {

        if (position == -1) {
            return;
        }
        Intent detailIntent = new Intent(this, MovieDetailsActivity.class);
        detailIntent.putExtra(KEY_MOVIE_ID, moviesList.get(position).getId());
        startActivity(detailIntent);
    }

    @Override
    public void showEmptyView() {
        rvMovieList.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        rvMovieList.setVisibility(View.VISIBLE);
        tvEmptyView.setVisibility(View.GONE);
    }
}
