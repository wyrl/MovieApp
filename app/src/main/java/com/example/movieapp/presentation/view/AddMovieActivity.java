package com.example.movieapp.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

        if(checkValid()){
            Intent intent = new Intent();
            intent.putExtra("movie", movie);
            setResult(RESULT_OK, intent);

            clearFields();

            finish();
        }
    }

    private boolean checkValid() {
        binding.txtErrMsg.setText("");

        if(isEmpty(movie.getTitle())){
            setErrorMessage(getString(R.string.error_msg_empty, "title"));
            return false;
        }

        if(isEmpty(movie.getYearReleased())){
            setErrorMessage(getString(R.string.error_msg_empty, "year released"));
            return false;
        }

        if(isEmpty(movie.getRatings())){
            setErrorMessage(getString(R.string.error_msg_empty, "ratings"));
            return false;
        }

        if(isEmpty(movie.getImageUrl())){
            setErrorMessage(getString(R.string.error_msg_empty, "image link"));
            return false;
        }

        if(isEmpty(movie.getDescription())){
            setErrorMessage(getString(R.string.error_msg_empty, "description"));
            return false;
        }

        return true;
    }

    private boolean isEmpty(Object txt){
        return txt == null || ((String)txt).isEmpty();
    }

    private void setErrorMessage(String errorMessage){
        binding.txtErrMsg.setVisibility(View.VISIBLE);
        binding.txtErrMsg.setText(errorMessage);
    }

    private void clearFields() {
        movie = null;
    }
}