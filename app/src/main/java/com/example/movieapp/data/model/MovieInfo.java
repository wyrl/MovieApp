package com.example.movieapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieInfo {
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Released")
    @Expose
    private String released;
    @SerializedName("Plot")
    @Expose
    private String plot;
    @SerializedName("imdbRating")
    @Expose
    private String imdbRating;
    @SerializedName("Images")
    @Expose
    private List<String> images;

    public MovieInfo(String title, String released, String plot, String imdbRating, List<String> images) {
        this.title = title;
        this.released = released;
        this.plot = plot;
        this.imdbRating = imdbRating;
        this.images = images;
    }

    @SerializedName("totalSeasons")



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleased() {
        return released;
    }

    public String getPlot() {
        return plot;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public List<String> getImages() {
        return images;
    }

}