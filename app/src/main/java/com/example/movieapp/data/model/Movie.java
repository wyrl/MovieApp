package com.example.movieapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    private String title;
    private String description;
    private String yearReleased;
    private double ratings;
    private String imageUrl;

    public Movie(String title){
        this.setTitle(title);
        this.setDescription("");
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

    public static List<Movie> convertFrom(List<MovieInfo> movieInfoList){
        List<Movie> movies = new ArrayList<>();
        for (MovieInfo info: movieInfoList) {
            Movie movie = new Movie(info.getTitle());
            movie.setYearReleased(info.getReleased());
            movie.setImageUrl(info.getImages().get(0));

            if(!info.getImdbRating().equals("N/A")){
                movie.setRatings(Double.parseDouble(info.getImdbRating()));
            }
            else{
                movie.setRatings(0);
            }
            movie.setDescription(info.getPlot());

            movies.add(movie);
        }

        return movies;
    }
}
