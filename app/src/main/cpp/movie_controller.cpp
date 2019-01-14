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

    std::string result = new_movies();
    return env->NewStringUTF(result.c_str());
}

std::string new_movies()
{
    std::string result;
    std::ostringstream stream;
    stream << "test";
    result = stream.str();
    return result;
}
