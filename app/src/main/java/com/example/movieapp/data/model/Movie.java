package com.example.movieapp.data.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Movie extends BaseObservable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @Bindable
    private String title;

    @ColumnInfo(name = "description")
    @Bindable
    private String description;

    @ColumnInfo(name = "year_released")
    @Bindable
    private String dateReleased;

    @ColumnInfo(name = "ratings")
    @Bindable
    private String ratings;

    @ColumnInfo(name = "image_url")
    @Bindable
    private String imageUrl;

    @ColumnInfo(name = "backdrop_image_url")
    @Bindable
    private String backdropImageUrl;

    public Movie() {
    }

    public Movie(String title, String description, String dateReleased, String ratings, String imageUrl, String backdropImageUrl) {
        this.title = title;
        this.description = description;
        this.dateReleased = dateReleased;
        this.ratings = ratings;
        this.imageUrl = imageUrl;
        this.backdropImageUrl = backdropImageUrl;
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
        notifyPropertyChanged(BR.title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public String getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(String dateReleased) {
        this.dateReleased = dateReleased;
        notifyPropertyChanged(BR.dateReleased);
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
        notifyPropertyChanged(BR.dateReleased);
    }

    public String getBackdropImageUrl() {
        return backdropImageUrl;
    }

    public void setBackdropImageUrl(String backdropImageUrl) {
        this.backdropImageUrl = backdropImageUrl;
        notifyPropertyChanged(BR.backdropImageUrl);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    public static List<Movie> convertFrom(List<MovieInfo> movieInfoList) {
        List<Movie> movies = new ArrayList<>();
        for (MovieInfo info : movieInfoList) {
            movies.add(new Movie(
                    info.getTitle(),
                    info.getPlot(),
                    info.getReleased(),
                    info.getImdbRating(),
                    info.getImages().get(0),
                    info.getImages().get(1)
            ));
        }

        return movies;
    }

    public MovieInfo getMovieInfo() {
        return new MovieInfo(
                title,
                dateReleased,
                description,
                String.valueOf(ratings),
                Arrays.asList(imageUrl, backdropImageUrl)
        );
    }

    public String toString() {
        return "Movie Title: " + title + ", Date Released: " + dateReleased;
    }


}
