package com.example.movieapp.presentation.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieapp.R;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.databinding.ActivityAddMovieBinding;
import com.example.movieapp.presentation.viewmodel.AddMovieViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMovieActivity extends AppCompatActivity implements View.OnClickListener {

    final static String TAG = "AddMovieActivity";
    ActivityAddMovieBinding binding;

    AddMovieViewModel viewModel;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie);
        movie = new Movie();
        binding.setMovie(movie);
        binding.btnAddMovie.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.btnPickDate.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(AddMovieViewModel.class);

        observer();
    }

    private void observer() {
        viewModel.getApiResponse().observe(this, response -> {
            if (response.isSuccess()) {
                clearFields();
                finish();
            } else {
                Log.e(TAG, response.getMessage());
                Toast.makeText(AddMovieActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnBack) {
            onBackClick();
            return;
        }

        if (view.getId() == R.id.btnAddMovie) {
            onAddMovieClick();
            return;
        }

        if (view.getId() == R.id.btnPickDate) {
            onPickDate();
            return;
        }
    }

    private void onBackClick() {
        finish();
    }

    private void onAddMovieClick() {
        Log.d(TAG, "Movie: " + movie.toString());
        if (checkValid()) {
            viewModel.addMovie(movie.getMovieInfo());
        }
    }

    private boolean checkValid() {
        binding.txtErrMsg.setText("");

        if (isEmpty(movie.getTitle())) {
            setErrorMessage(getString(R.string.error_msg_empty, "title"));
            return false;
        }

        if (isEmpty(movie.getDateReleased())) {
            setErrorMessage(getString(R.string.error_msg_empty, "year released"));
            return false;
        }

        if (isEmpty(movie.getRatings())) {
            setErrorMessage(getString(R.string.error_msg_empty, "ratings"));
            return false;
        }

        if (isEmpty(movie.getBackdropImageUrl())) {
            setErrorMessage(getString(R.string.error_msg_empty, "image link"));
            return false;
        }

        if (isEmpty(movie.getDescription())) {
            setErrorMessage(getString(R.string.error_msg_empty, "description"));
            return false;
        }

        return true;
    }

    private boolean isEmpty(Object txt) {
        return txt == null || ((String) txt).isEmpty();
    }

    private void setErrorMessage(String errorMessage) {
        binding.txtErrMsg.setVisibility(View.VISIBLE);
        binding.txtErrMsg.setText(errorMessage);
    }

    private void clearFields() {
        movie = null;
    }

    private void onPickDate() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (datePicker, y, m, d) -> {
                final String format = String.format("%d-%d-%d", y, m + 1, d);
                Log.d(TAG, "Date format: " + format);
                final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                final SimpleDateFormat convert = new SimpleDateFormat("MMMM dd, YYYY");
                try {
                    Date date = parser.parse(format);
                    String fullDate = convert.format(date);
                    movie.setDateReleased(fullDate);

                    Log.d(TAG, "Full Date: " + movie.getDateReleased());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            },
            year, month, day
        );

        datePickerDialog.show();
    }
}