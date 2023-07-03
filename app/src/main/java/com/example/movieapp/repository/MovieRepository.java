package com.example.movieapp.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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
        loadData();
    }

    private void loadData(){
        Log.d("MovieRepository", "loadData");
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            List<Movie> movieList = db.movieDao().getAll();
            if(movieList.size() != 0){
                movies.postValue(movieList);
                Log.d("MovieRepository", "Load from database");
            }
            else{
                fetchFromAPI();
                Log.d("MovieRepository", "Load from API");
            }
        });
    }

    private void fetchFromAPI(){
        Log.d("MovieRepository", "fetchFromAPI");
        RetrofitInstance.api.getMovies().enqueue(new Callback<List<MovieInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieInfo>> call, @NonNull Response<List<MovieInfo>> response) {
                if(response.isSuccessful()){
                    List<Movie> movieList = Movie.convertFrom(response.body());
                    saveIntoDatabase(movieList);
                    movies.setValue(movieList);
                }
                else{
                    Log.d("MovieRepository", "onReponse --> failure");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MovieInfo>> call, @NonNull Throwable t) {
                Log.d("MovieRepository", "Failure...");
            }
        });
    }

    private void saveIntoDatabase(List<Movie> movieList){
        if(movies.getValue() == null){
            MovieDatabase.databaseWriteExecutor.execute(() -> {
                db.movieDao().insertAll(movieList);
            });
        }
    }



    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
