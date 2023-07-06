package com.example.movieapp.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.model.MovieInfo;
import com.example.movieapp.data.service.AddMovieListener;
import com.example.movieapp.data.service.ApiResponse;
import com.example.movieapp.repository.MovieRepository;

public class AddMovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    private MutableLiveData<ApiResponse> response = new MutableLiveData<>();



    public AddMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
    }

    public void addMovie(MovieInfo movieInfo){
        repository.addMovie(movieInfo, new AddMovieListener() {
            @Override
            public void onAddedMovie() {
                response.postValue(new ApiResponse(true, ""));
            }

            @Override
            public void onAddingMovieFailure(String errorMessage) {
                response.postValue(new ApiResponse(false, errorMessage));
            }
        });
    }

    public LiveData<ApiResponse> getApiResponse(){
        return response;
    }
}
