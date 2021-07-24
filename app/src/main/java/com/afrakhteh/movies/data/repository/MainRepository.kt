package com.afrakhteh.movies.data.repository

import com.afrakhteh.movies.data.api.ApiHelper
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import retrofit2.Response

class MainRepository(
    private var api:ApiHelper
)
{
    suspend fun login(
        email: String,
        password: String
    ) = api.login(email,password)

    suspend fun  getLimitedNewMovie() : Response<List<Movies>> {
       return api.getLimitedNewMovie()
    }

    suspend fun getLimitedPopular() : Response<List<Movies>>{
        return api.getLimitedPopular()
    }

    suspend fun getAllNewMovie(): Response<List<Movies>>{
        return api.getAllNewMovie()
    }

    suspend fun getAllPopularMovie() : Response<List<Movies>>{
        return api.getAllPopularMovie()
    }

    suspend fun getAllMoviesActors(movie_id: Int): Response<List<Actors>>{
        return api.getAllMoviesActors(movie_id)
    }

}