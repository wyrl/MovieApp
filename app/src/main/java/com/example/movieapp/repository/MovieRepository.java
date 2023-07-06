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
import com.example.movieapp.data.service.AddMovieListener;
import com.example.movieapp.data.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final String TAG = "MovieRepository";
    private final MutableLiveData<List<Movie>> movies;
    private MovieDatabase db;
    private Context context;

    public MovieRepository(Application application){
        context = application.getApplicationContext();
        movies = new MutableLiveData<>(new ArrayList<>());
        db = MovieDatabase.getDatabase(context);
        loadData();
    }

    private void loadData(){
        Log.d(TAG, "loadData");
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            List<Movie> movieList = db.movieDao().getAll();
            if(movieList.size() != 0){
                movies.postValue(movieList);
                Log.d(TAG, "Load from database");
            }
            else{
                fetchFromAPI();
                Log.d(TAG, "Load from API");
            }
        });
    }

    public void refreshListFromLocal(){
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            List<Movie> movieList = db.movieDao().getAll();
            movies.postValue(movieList);
        });
    }

    private void fetchFromAPI(){
        Log.d(TAG, "fetchFromAPI");
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
                Log.d(TAG, "Failure: fetFromAPI -> " + t.getMessage());
            }
        });
    }

    private void saveIntoDatabase(List<Movie> movieList){
        Log.d(TAG, "saveIntoDatabase");
        if(movies.getValue() == null || movies.getValue().size() == 0){
            MovieDatabase.databaseWriteExecutor.execute(() -> {
                Log.d(TAG, "Movie List Count: " + movieList.size());
                db.movieDao().insertAll(movieList);
            });
        }
    }

    private void insertMovie(Movie movie){
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            db.movieDao().insert(movie);
        });
    }

    public void addMovie(MovieInfo movieInfo, AddMovieListener callback){
        RetrofitInstance.api.addMovie(movieInfo).enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {

                if(response.isSuccessful()){
                    MovieInfo info = response.body();
                    Log.d(TAG, "Success: addMovie -> isSuccess: " + info.getTitle());
                    insertMovie(new Movie(
                        info.getTitle(),
                            info.getPlot(),
                            info.getReleased(),
                            info.getImdbRating(),
                            info.getImages().get(0),
                            info.getImages().get(1)
                    ));
                    callback.onAddedMovie();
                }
                else{
                    callback.onAddingMovieFailure("Response Failure: " + response.message());
                    Log.e(TAG, "Failure: addMovie -> " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                callback.onAddingMovieFailure("Response Failure: " + t.getMessage());
                Log.e(TAG, "Failure: addMovie -> " + t.getMessage());
            }
        });
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
