package com.example.movieapp.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movieapp.data.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Insert
    void insertAll(List<Movie> movies);

    @Insert
    void insert(Movie movie);
}
