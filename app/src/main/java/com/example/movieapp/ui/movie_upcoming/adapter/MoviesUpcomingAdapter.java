/**
 * @file MovieAdapter.java
 * @brief Adapter class responsible for showing movies data.
 * @author Nicolas Escobar
 * @date 28/05/2019
 */
package com.example.movieapp.ui.movie_upcoming.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.movieapp.R;
import com.example.movieapp.model.Movie;
import com.example.movieapp.ui.movie_popular.adapter.MoviesPopularAdapter;
import com.example.movieapp.ui.movie_upcoming.MovieUpcomingActivity;
import com.example.movieapp.utils.network.ApiConnection;

import java.util.ArrayList;
import java.util.List;

public class MoviesUpcomingAdapter extends RecyclerView.Adapter<MoviesUpcomingAdapter.CustomViewHolder> implements Filterable {

    private MovieUpcomingActivity movieUpcomingActivity;
    private List<Movie> moviesList;
    private List<Movie> originalMovieList;

    private String title;


    public MoviesUpcomingAdapter(MovieUpcomingActivity movieUpcomingActivity, List<Movie> moviesList) {
        this.movieUpcomingActivity = movieUpcomingActivity;
        this.moviesList = moviesList;
        this.originalMovieList = moviesList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card_view, viewGroup, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {

        try {

            Movie movie = moviesList.get(position);

            holder.tvTitle.setText(movie.getTitle());
            holder.tvRatings.setText(String.valueOf(movie.getRating()));
            holder.tvRelease.setText(movie.getReleaseDate());

            // Load album image with the Glide library
            Glide.with(movieUpcomingActivity)
                    .load(ApiConnection.IMAGE_BASE_URL + movie.getThumbPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.pgLoadImage.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.pgLoadImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                    .into(holder.ivThumb);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieUpcomingActivity.onMovieItemClick(position);
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    /**
     * This function will set parameters for Filter
     * @param title -By title.
     */

    public void setFilterParameter(String title) {
        this.title = title;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                List<Movie> filteredResults = null;
                if (title.isEmpty()) {
                    filteredResults = originalMovieList;
                } else {
                    filteredResults = getFilteredResults(title);
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesList = (List<Movie>) filterResults.values;
                MoviesUpcomingAdapter.this.notifyDataSetChanged();

                if (getItemCount() == 0) {
                    movieUpcomingActivity.showEmptyView();
                } else {
                    movieUpcomingActivity.hideEmptyView();
                }
            }
        };
    }

    /**
     * This function will filter the data by using from and to date.
     * @param title - To release date.
     * @return List of movies which satisfies the criteria
     */

    protected List<Movie> getFilteredResults(String title) {

        List<Movie> results = new ArrayList<>();

        String filterPattern = title.toLowerCase().trim();

        for (Movie movie : originalMovieList) {

            if (movie.getTitle().isEmpty()) {
                continue;
            }

            if (movie.getTitle().toLowerCase().contains(filterPattern)) {
                results.add(movie);
            }
        }
        return results;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvRatings;
        TextView tvRelease;
        ImageView ivThumb;
        ProgressBar pgLoadImage;

        CustomViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvRelease = itemView.findViewById(R.id.tv_release_date);
            tvRatings = itemView.findViewById(R.id.tv_movie_ratings);
            ivThumb = itemView.findViewById(R.id.iv_movie_thumb);
            pgLoadImage = itemView.findViewById(R.id.pb_load_image);
        }
    }
}
