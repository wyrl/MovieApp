package com.example.movieapp.presentation.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.model.MovieInfo;
import com.example.movieapp.repository.AddMovieListener;
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

    public void addMovie(MovieInfo movieInfo, AddMovieListener callback){
        repository.addMovie(movieInfo, m -> {
            movieList.getValue().add(new Movie(
                    m.getTitle(),
                    m.getPlot(),
                    m.getReleased(),
                    Double.parseDouble(m.getImdbRating()),
                    m.getImages().get(0)
            ));
            callback.onAddedMovie(m);
        });
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
