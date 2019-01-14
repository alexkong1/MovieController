//
//  MovieController.hpp
//  Highrise
//
//  Created by Jimmy Xu on 12/19/18.
//  Copyright © 2019 Highrise. All rights reserved.
//

#ifndef MovieController_hpp
#define MovieController_hpp

#include <string>
#include <vector>
#include <map>
#include <jni.h>


std::string jstring2string(JNIEnv *env, jstring jStr);

extern "C" JNIEXPORT jstring JNICALL
Java_com_alexkong_movie_1controller_MainActivity_movieDetailsInterface(JNIEnv* env, jobject obj, jstring path);

extern "C" JNIEXPORT jstring JNICALL
Java_com_alexkong_movie_1controller_MainActivity_movieControllerInterface(JNIEnv* env, jobject);


namespace movies {
    class Actor {
    public:
        std::string name;
        int age;
        
        //optional challenge 1: Load image from URL
        std::string imageUrl;
    };
    
    class Movie {
    public:
        std::string name;
        int lastUpdated;
        
    };
    
    class MovieDetail {
    public:
        std::string name;
        float score;
        std::vector<Actor> actors;
        std::string description;
    };
    
    class MovieController {
    private:
        std::vector<Movie*> _movies;
        std::map<std::string, MovieDetail*> _details;

    public:
        MovieController() {
            //populate data
            for (int i = 0; i < 10; i++) {
                auto movie = new Movie();
                movie->name = "Top Gun " + std::to_string(i);
                movie->lastUpdated = i * 10000;
                _movies.push_back(movie);
                
                auto movieDetail = new MovieDetail();
                movieDetail->name = movie->name;
                movieDetail->score = rand() % 10;
                movieDetail->description = "As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.";
                
                auto tomCruise = Actor();
                tomCruise.name = "Tom Cruise";
                tomCruise.age = 50;
                
                auto valKilmer = Actor();
                valKilmer.name = "Val Kilmer";
                valKilmer.age = 46;
                valKilmer.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg";
                
                movieDetail->actors.push_back(tomCruise);
                movieDetail->actors.push_back(valKilmer);
                
                if (i % 2 == 0) {
                    auto timRobbins = Actor();
                    timRobbins.name = "Tim Robbins";
                    timRobbins.age = 55;
                    timRobbins.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg";
                    
                    movieDetail->actors.push_back(timRobbins);
                } else {
                    auto jenniferConnelly = Actor();
                    jenniferConnelly.name = "Jennifer Connelly";
                    jenniferConnelly.age = 39;
                    jenniferConnelly.imageUrl = "https://m.media-amazon.com/images/M/MV5BOTczNTgzODYyMF5BMl5BanBnXkFtZTcwNjk4ODk4Mw@@._V1_UY317_CR12,0,214,317_AL_.jpg";
                    
                    movieDetail->actors.push_back(jenniferConnelly);
                }
                
                _details[movie->name] = movieDetail;
            }
        }
        
        //Returns list of movies
        std::vector<Movie*> getMovies() {
            return _movies;
        }
        
        //Returns details about a specific movie
        MovieDetail* getMovieDetail(std::string name) {
            for (auto item:_details) {
                if (item.second->name == name) {
                    return item.second;
                }
            }
            return nullptr;
        }
                

    };
}

std::string get_movies_as_json();

std::vector<movies::Movie *> get_movies() {
    return movies::MovieController().getMovies();
}

std::string get_details_as_json(std::string name);

movies::MovieDetail* get_movie_detail(std::string name) {
    return movies::MovieController().getMovieDetail(name);
}

#endif /* MovieController_hpp */
