package com.example.movieapp.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.model.Movie;
import com.example.movieapp.repository.MovieRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MovieRepository repository;

    private final LiveData<List<Movie>> movieList;
    private final MutableLiveData<Movie> selectedMovie;

    public MoviesViewModel(@NonNull Application application){
        super(application);

        repository = new MovieRepository(application);

        movieList = repository.getMovies();

        selectedMovie = new MutableLiveData<>();
    }
    public LiveData<List<Movie>> getMovieList(){
        return movieList;
    }

    public LiveData<Movie> getSelectedMovie(){
        return selectedMovie;
    }

    public void updateSelectedMovie(Movie movie){
        selectedMovie.setValue(movie);
    }



}
