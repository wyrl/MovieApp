package com.example.movieapp.data.service;

import com.example.movieapp.data.model.MovieInfo;

public interface AddMovieListener {
    void onAddedMovie(MovieInfo movieInfo);
    void onAddingMovieFailure(String errorMessage);
}
