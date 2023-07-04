package com.example.movieapp.presentation.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.presentation.adapter.MoviesAdapter;
import com.example.movieapp.presentation.viewmodel.MoviesViewModel;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ClickHandlers, View.OnClickListener {

    ActivityMainBinding binding;
    MoviesViewModel viewModel;
    MoviesAdapter adapter;

    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.appBarLayout.btnAdd.setOnClickListener(this);
        setupRecyclerAdapter();
    }

    private void setupRecyclerAdapter(){
        viewModel.getMovieList().observe(this, movieList -> {
            if(viewModel.getSelectedMovie().getValue() == null){
                viewModel.updateSelectedMovie(movieList.get(0));
            }
            Log.i(TAG, "Movie count: " + movieList.size());
            adapter = new MoviesAdapter(movieList, this);
            binding.recyclerView.setAdapter(adapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }

    @Override
    public void onSelected(Movie movie) {
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();

        Log.i(TAG, "Orientation: " + config.orientation);

        if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
        else if(config.orientation  == Configuration.ORIENTATION_LANDSCAPE){
            viewModel.updateSelectedMovie(movie);
            loadMovieDetailsImage(movie.getImageUrl());
        }
    }

    private void loadMovieDetailsImage(String url){
        Glide.with(this)
                .load(url)
                .into(binding.includedLayout.imageView);
    }

    @Override
    public void onClick(View v) {

        // On Click Add button
        Intent intent = new Intent(this, AddMovieActivity.class);
        startActivityIfNeeded(intent, AddMovieActivity.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AddMovieActivity.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Movie movie = (Movie) data.getSerializableExtra("movie");
                Log.d(TAG, "Add movie result");
                Log.d(TAG, "Movie Title: " + movie.getTitle());
                viewModel.addMovie(movie.getMovieInfo(), movieInfo -> {
                    adapter.setMovieList(viewModel.getMovieList().getValue());
                    Log.d(TAG, "Movie List Count: " + viewModel.getMovieList().getValue().size());

                });
            }
        }
    }
}