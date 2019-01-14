//
// Created by Alex on 1/13/2019.
//

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include "MovieController.hpp"

JNIEXPORT jstring JNICALL
Java_com_alexkong_movie_1controller_MainActivity_movieControllerInterface(
        JNIEnv* env,
        jobject /* this */) {

    //std::vector<movies::Movie> result = get_movies();
    std::string result = get_new_movies();
    return env->NewStringUTF(result.c_str());
}

std::string get_new_movies()
{
    std::string result;
    std::ostringstream stream;
    stream << "test";
    std::vector<movies::Movie *> moviesList = get_movies();
    for (std::vector<movies::Movie *>::size_type i = 0; i != moviesList.size(); i++) {
        stream << moviesList[i]->name;
    }
    result = stream.str();
    return result;
}
