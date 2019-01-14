package com.alexkong.movie_controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnClickMovieListener {

    static
    {
        System.loadLibrary("movie_controller");
    }
    public native String movieControllerInterface();

    public native String movieDetailsInterface(String name);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String moviesJson = movieControllerInterface();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MovieListFragment.newInstance(moviesJson))
                .commit();

    }

    @Override
    public void onMovieSelected(String name) {
        Log.e("MOVIE DETAIL", name);

        String detailsJson = movieDetailsInterface(name);

        try {
            Movies.MovieDetail detail = new Gson().fromJson(detailsJson, Movies.MovieDetail.class);
            if (detail != null) Log.e("MOVIE DETAIL", detail.description);
        } catch (Exception e) {
            Log.e("MOVIE DETAIL", e.toString());
        }
    }
}
