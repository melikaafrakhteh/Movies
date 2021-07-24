package com.afrakhteh.movies.data.api

import androidx.lifecycle.MutableLiveData
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.util.nework.Resource
import retrofit2.Response

interface ApiHelper {

    suspend fun login(email: String, password: String): MutableLiveData<String>

    suspend fun getLimitedNewMovie(): Response<List<Movies>>

    suspend fun getLimitedPopular(): Response<List<Movies>>

    suspend fun getAllNewMovie() : Response<List<Movies>>

    suspend fun getAllPopularMovie() :Response<List<Movies>>

    suspend fun getAllMoviesActors(movie_id: Int) : Response<List<Actors>>
}