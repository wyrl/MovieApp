package com.example.movieapp.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.movieapp.data.model.MovieInfo;
import com.example.movieapp.data.service.AddMovieListener;
import com.example.movieapp.repository.MovieRepository;

public class AddMovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public AddMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
    }

    public void addMovie(MovieInfo movieInfo, AddMovieListener callback){
        repository.addMovie(movieInfo, callback);
    }
}
