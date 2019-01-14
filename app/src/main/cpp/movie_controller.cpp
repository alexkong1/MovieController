//
// Created by Alex on 1/13/2019.
//

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include "MovieController.hpp"


JNIEXPORT jstring JNICALL
Java_com_alexkong_movie_1controller_MovieListFragment_movieControllerInterface(
        JNIEnv* env,
        jobject /* this */) {

    std::string result = get_movies_as_json();
    return env->NewStringUTF(result.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_alexkong_movie_1controller_MovieDetailsFragment_movieDetailsInterface(
        JNIEnv* env,
        jobject obj,
        jstring name) {

    std::string result = get_details_as_json(jstring2string(env, name));
    return env->NewStringUTF(result.c_str());
}


std::string get_movies_as_json()
{
    std::string result;
    std::ostringstream stream;

    std::vector<movies::Movie *> moviesList = get_movies();

    stream << "{ \"movies\":[";

    for (std::vector<movies::Movie *>::size_type i = 0; i != moviesList.size(); i++) {
        stream << "{\"name\": \"" << moviesList[i]->name << "\", "
        << "\"lastUpdated\": " << moviesList[i]->lastUpdated << "}";
        if (i < moviesList.size() -1)
            stream << ", ";
    }
    stream << "]}";
    result = stream.str();
    return result;
}

std::string get_details_as_json(std::string name)
{
    std::string result;
    std::ostringstream stream;

    movies::MovieDetail* detail = get_movie_detail(name);
    stream << "{\"name\": \"" << detail->name << "\", "
           << "\"score\": " << detail->score << ", \"actors\": [";

    std::vector<movies::Actor> actors = detail->actors;
    for (std::vector<movies::Actor>::size_type i = 0; i != actors.size(); i++) {
        stream << "{\"name\": \"" << actors[i].name << "\", " << "\"age\": " << actors[i].age << ", "
        << "\"imageUrl\": \"" << actors[i].imageUrl << "\"}";
        if (i < actors.size() - 1)
            stream << ", ";
    }

    stream << "], \"description\": \"" << detail->description << "\"}";
    result = stream.str();
    return result;
}

std::string jstring2string(JNIEnv *env, jstring jStr)
{
    std::string ret;
    if (jStr) {
        const jclass stringClass = env->GetObjectClass(jStr);
        const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes",
                                                    "(Ljava/lang/String;)[B");
        const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
                                                                           env->NewStringUTF(
                                                                                   "UTF-8"));

        size_t length = (size_t) env->GetArrayLength(stringJbytes);
        jbyte *pBytes = env->GetByteArrayElements(stringJbytes, NULL);

        ret = std::string((char *) pBytes, length);
        env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

        env->DeleteLocalRef(stringJbytes);
        env->DeleteLocalRef(stringClass);
    }
    return ret;
}
