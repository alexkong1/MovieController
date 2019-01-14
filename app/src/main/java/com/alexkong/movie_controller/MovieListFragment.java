package com.alexkong.movie_controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {

    static {
        System.loadLibrary("movie_controller");
    }

    public native String movieControllerInterface();

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_list, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUi(view);
    }

    private void initializeUi(View view) {

        if (getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        String moviesJson = movieControllerInterface();

        List<Movies.Movie> movies = new ArrayList<>();

        try {
            Movies.MovieController movieList = new Gson().fromJson(moviesJson, Movies.MovieController.class);
            if (movieList != null) movies = movieList.getMovies();
        } catch (Exception e) {
            Log.e("MOVIE CONTROLLER", e.toString());
        }

        RecyclerView recyclerView = view.findViewById(R.id.movie_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MovieListAdapter(movies, (MainActivity) getActivity()));
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        MovieViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_movie_title);
        }
    }

    class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

        private List<Movies.Movie> movies;
        private OnClickMovieListener listener;

        MovieListAdapter(List<Movies.Movie> movies, OnClickMovieListener listener) {
            this.movies = movies;
            this.listener = listener;
        }

        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.item_movie, viewGroup, false);
            return new MovieViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
            final Movies.Movie movie = movies.get(movieViewHolder.getAdapterPosition());
            movieViewHolder.title.setText(movie.name);
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMovieSelected(movie.name);
                }
            });
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    public interface OnClickMovieListener {
        void onMovieSelected(String name);
    }
}
