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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class MovieDetailsFragment extends Fragment {

    private static String ARGS_MOVIE_NAME = "movieName";

    static {
        System.loadLibrary("movie_controller");
    }

    public native String movieDetailsInterface(String name);

    public static MovieDetailsFragment newInstance(String name) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_MOVIE_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUi(view);
    }

    private void initializeUi(View root) {

        if (getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String detailsJson = movieDetailsInterface(getArguments().getString(ARGS_MOVIE_NAME));

        Movies.MovieDetail detail = new Gson().fromJson(detailsJson, Movies.MovieDetail.class);

        RecyclerView recyclerView = root.findViewById(R.id.movie_details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ActorsAdapter(detail.actors));
    }

    class ActorViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView age;
        ImageView image;

        ActorViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.actor_name);
            age = view.findViewById(R.id.actor_age);
            image = view.findViewById(R.id.actor_image);
        }
    }

    class ActorsAdapter extends RecyclerView.Adapter<ActorViewHolder> {

        List<Movies.Actor> actors;

        ActorsAdapter(List<Movies.Actor> actors) {
            this.actors = actors;
        }

        @NonNull
        @Override
        public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.item_actor, viewGroup, false);

            return new ActorViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ActorViewHolder actorViewHolder, int i) {
            Movies.Actor actor = actors.get(actorViewHolder.getAdapterPosition());

            actorViewHolder.name.setText(actor.name);
            actorViewHolder.age.setText(String.format(getString(R.string.actor_age), actor.age));
            if (actor.imageUrl != null && !actor.imageUrl.isEmpty())
                Glide.with(MovieDetailsFragment.this)
                        .load(actor.imageUrl)
                        .into(actorViewHolder.image);
        }

        @Override
        public int getItemCount() {
            return actors.size();
        }
    }
}
