/**
 * @file MoviesTopAdapter.java
 * @brief Adapter class responsible for showing top rated movies data.
 * @author Nicolas Escobar Cruz
 * @date 28/05/2019
 */
package com.example.movieapp.ui.movie_top.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.movieapp.ui.movie_top.MovieTopActivity;
import com.example.movieapp.utils.network.ApiConnection;

import java.util.List;

public class MoviesTopAdapter extends RecyclerView.Adapter<MoviesTopAdapter.CustomViewHolder> {

    private MovieTopActivity movieTopActivity;
    private List<Movie> moviesList;

    public MoviesTopAdapter(MovieTopActivity movieTopActivity, List<Movie> moviesList) {
        this.movieTopActivity = movieTopActivity;
        this.moviesList = moviesList;
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
            Glide.with(movieTopActivity)
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
                    movieTopActivity.onMovieItemClick(position);
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

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvRatings;
        public TextView tvRelease;
        public ImageView ivThumb;
        public ProgressBar pgLoadImage;

        public CustomViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvRelease = itemView.findViewById(R.id.tv_release_date);
            tvRatings = itemView.findViewById(R.id.tv_movie_ratings);
            ivThumb = itemView.findViewById(R.id.iv_movie_thumb);
            pgLoadImage = itemView.findViewById(R.id.pb_load_image);
        }
    }
}
