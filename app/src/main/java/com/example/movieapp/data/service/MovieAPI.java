package com.example.movieapp.data.service;

import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.model.MovieInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MovieAPI {
    @GET("/movies")
    Call<List<MovieInfo>> getMovies();

    @Headers({ "Authorization: dGVzdDpzZWNyZXQ=" })
    @POST("/add-movie")
    Call<MovieInfo> addMovie(@Body MovieInfo movieInfo);
}
