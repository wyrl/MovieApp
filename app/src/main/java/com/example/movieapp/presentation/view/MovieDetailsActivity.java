package com.example.movieapp.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.databinding.ActivityMovieDetailsBinding;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private final static String TAG = MovieDetailsActivity.class.getSimpleName();
    ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        Intent i = getIntent();
        Movie movie = (Movie) i.getSerializableExtra("movie");
        binding.setMovie(movie);
        loadImages(movie.getImageUrl(), movie.getBackdropImageUrl());
    }

    private void loadImages(String imageUrl, String backdropImageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(binding.includedLayout.imageView);

        Log.d(TAG, "Background Image Url: " + backdropImageUrl);

        Glide.with(this)
                .load(backdropImageUrl)
                .into(binding.includedLayout.backgroundImage);
    }

    public void onBackClicked(View view) {
        finish();
    }
}