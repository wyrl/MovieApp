package com.example.movieapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.model.MovieInfo;
import com.example.movieapp.data.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final MutableLiveData<List<Movie>> movies;

    public MovieRepository(){
        movies = new MutableLiveData<>();

        RetrofitInstance.api.getMovies().enqueue(new Callback<List<MovieInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieInfo>> call, @NonNull Response<List<MovieInfo>> response) {
                if(response.isSuccessful()){
                    movies.setValue(Movie.convertFrom(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MovieInfo>> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
