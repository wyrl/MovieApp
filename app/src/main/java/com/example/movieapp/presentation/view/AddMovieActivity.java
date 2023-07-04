package com.example.movieapp.presentation.view;

import android.content.Intent;
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

    final static String TAG = "AddMovieActivity";
    public static int REQUEST_CODE = 1;
    ActivityAddMovieBinding binding;

    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie);
        movie = new Movie();
        binding.setMovie(movie);
        binding.btnAddMovie.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnBack){
            onBackClick(view);
            return;
        }

        if(view.getId() == R.id.btnAddMovie){
            onAddMovieClick(view);
            return;
        }
    }


    private void onBackClick(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onAddMovieClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("movie", movie);
        setResult(RESULT_OK, intent);
        finish();
    }
}