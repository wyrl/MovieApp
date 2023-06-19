package com.example.movieapp.presentation.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.databinding.MovieItemBinding;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{
    final List<Movie> movieList;
    final MoviesAdapter.ClickHandlers clickHandlers;
    public MoviesAdapter(List<Movie> movieList, MoviesAdapter.ClickHandlers clickHandlers){
        this.movieList = movieList;
        this.clickHandlers = clickHandlers;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = movieList.get(position);


        Log.d("MoviesAdapter", "Movie Title: " + movie.getTitle());
        holder.binding.setMovie(movie);
        holder.itemView.setOnClickListener(v -> clickHandlers.onSelected(movie));

        Glide.with(holder.binding.getRoot())
                .load(movie.getImageUrl())
                .into(holder.binding.bgImg);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieItemBinding binding;
        public MovieViewHolder(@NonNull MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ClickHandlers{
        void onSelected(Movie movie);
    }
}
