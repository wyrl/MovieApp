package com.example.movieapp.data.service;

import com.example.movieapp.data.model.MovieInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {
    @GET("/movies")
    Call<List<MovieInfo>> getMovies();
}
