package com.alexkong.movie_controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("movie_controller");
    }
    public native String movieControllerInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String movies = movieControllerInterface();
        Log.e("MOVIE CONTROLLER", movies);

        try {
            Movies.MovieController movieList = new Gson().fromJson(movies, Movies.MovieController.class);
            if (movieList != null) {
                for (Movies.Movie movie : movieList.getMovies())
                    Log.e("MOVIE CONTROLLER", movie.name);
            }
        } catch (Exception e) {
            Log.e("MOVIE CONTROLLER", e.toString());
        }
    }
}
