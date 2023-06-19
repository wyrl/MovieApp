package com.example.movieapp.data.service;

import com.example.movieapp.data.model.MovieInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {
    @GET("/saniyusuf/406b843afdfb9c6a86e25753fe2761f4/raw/523c324c7fcc36efab8224f9ebb7556c09b69a14/")
    Call<List<MovieInfo>> getMovies();
}
