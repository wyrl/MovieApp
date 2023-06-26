package com.example.movieapp.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.database.MovieDatabase;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.model.MovieInfo;
import com.example.movieapp.data.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final MutableLiveData<List<Movie>> movies;
    private MovieDatabase db;
    private Context context;

    public MovieRepository(Application application){
        context = application.getApplicationContext();
        movies = new MutableLiveData<>();
        db = MovieDatabase.getDatabase(context);
        initRetrofit();
    }

    private void initDatabase(){

    }

    private void initRetrofit(){
        RetrofitInstance.api.getMovies().enqueue(new Callback<List<MovieInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieInfo>> call, @NonNull Response<List<MovieInfo>> response) {
                if(response.isSuccessful()){
                    List<Movie> movieList = Movie.convertFrom(response.body());
                    movies.setValue(movieList);
                    db.movieDao().insertAll(movieList);

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
