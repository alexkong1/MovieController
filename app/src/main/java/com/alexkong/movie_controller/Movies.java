package com.alexkong.movie_controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Movies {

    public class Movie {
        public String name;
        public int lastUpdated;
    }

    public class Actor {
        public String name;
        public int age;

        //optional challenge 1: Load image from URL
        public String imageUrl;
    }

    public class MovieDetail {
        public String name;
        public float score;
        public List<Actor> actors;
        public String description;
    }

    public class MovieController {
        private List<Movie> movies = new ArrayList<>();
        private Map<String, MovieDetail> details;

        public List<Movie> getMovies() {
            return movies;
        }
    }
}
