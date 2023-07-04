package com.example.movieapp.repository;

import com.example.movieapp.data.model.MovieInfo;

public interface AddMovieListener {
    void onAddedMovie(MovieInfo movieInfo);
}
