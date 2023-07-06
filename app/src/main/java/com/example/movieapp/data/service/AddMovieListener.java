package com.example.movieapp.data.service;

import com.example.movieapp.data.model.MovieInfo;

public interface AddMovieListener {
    void onAddedMovie();
    void onAddingMovieFailure(String errorMessage);
}
