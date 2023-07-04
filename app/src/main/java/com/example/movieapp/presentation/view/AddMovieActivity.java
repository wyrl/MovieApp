package com.example.movieapp.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieapp.R;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.model.MovieInfo;
import com.example.movieapp.databinding.ActivityAddMovieBinding;
import com.example.movieapp.presentation.viewmodel.MoviesViewModel;
import com.example.movieapp.repository.AddMovieListener;

public class AddMovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CODE = 1;
    ActivityAddMovieBinding binding;
    MoviesViewModel viewModel;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie);
        viewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        movie = new Movie();
        binding.setMovie(movie);
        binding.btnAddMovie.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        viewModel.addMovie(movie.getMovieInfo(), movieInfo -> {
            Log.d("AddMovieActivity", "Added Movie: " + movieInfo.getTitle());
            setResult(REQUEST_CODE);
            finish();
        });
    }
}