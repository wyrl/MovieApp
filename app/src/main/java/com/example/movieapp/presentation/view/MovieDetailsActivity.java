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

public class MovieDetailsActivity extends AppCompatActivity {

    ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        Intent i = getIntent();
        Movie movie = (Movie) i.getSerializableExtra("movie");
        binding.setMovie(movie);
        loadImage(movie.getImageUrl());
    }

    private void loadImage(String url){
        Glide.with(this)
                .load(url)
                .into(binding.includedLayout.imageView);
    }

    public void onBackClicked(View view){
        finish();
    }
}