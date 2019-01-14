package com.alexkong.movie_controller;

import java.util.List;

public class MovieController {

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
}
