package com.example.movieapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "year_released")
    private String yearReleased;

    @ColumnInfo(name = "ratings")
    private double ratings;

    @ColumnInfo(name = "image_url")
    private String imageUrl;
    public Movie(){}
    public Movie(String title, String description, String yearReleased, double ratings, String imageUrl) {
        this.title = title;
        this.description = description;
        this.yearReleased = yearReleased;
        this.ratings = ratings;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(String yearReleased) {
        this.yearReleased = yearReleased;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static List<Movie> convertFrom(List<MovieInfo> movieInfoList) {
        List<Movie> movies = new ArrayList<>();
        for (MovieInfo info : movieInfoList) {

            double ratings;
            if (!info.getImdbRating().equals("N/A")) {
                ratings = Double.parseDouble(info.getImdbRating());
            } else {
                ratings = 0;
            }

            movies.add(new Movie(
                    info.getTitle(),
                    info.getPlot(),
                    info.getReleased(),
                    ratings,
                    info.getImages().get(0)
            ));

        }

        return movies;
    }

    public MovieInfo getMovieInfo() {
        return new MovieInfo(
                title,
                yearReleased,
                description,
                String.valueOf(ratings),
                Collections.singletonList(imageUrl)
        );
    }

    public String toString() {
        return "Movie Title: " + title;
    }


}
