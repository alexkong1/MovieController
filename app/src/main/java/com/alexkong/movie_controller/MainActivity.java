package com.alexkong.movie_controller;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnClickMovieListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MovieListFragment.newInstance())
                .commit();

    }

    @Override
    public void onMovieSelected(String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MovieDetailsFragment.newInstance(name))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
