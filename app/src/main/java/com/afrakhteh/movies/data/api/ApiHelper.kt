package com.afrakhteh.movies.data.api

import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Comments
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.dataModel.Search
import retrofit2.Response

interface ApiHelper {

    suspend fun login(email: String, password: String): Response<String>

    suspend fun register(name: String, email: String, password: String): Response<String>

    suspend fun getLimitedNewMovie(): Response<List<Movies>>

    suspend fun getLimitedPopular(): Response<List<Movies>>

    suspend fun getAllNewMovie(): Response<List<Movies>>

    suspend fun getAllPopularMovie(): Response<List<Movies>>

    suspend fun getAllMoviesActors(movie_id: Int): Response<List<Actors>>

    suspend fun search(search: String): Response<Search>

    suspend fun sendComments(movie_id: Int, name: String, comment: String): Response<String>

    suspend fun getAllComments(movie_id: Int): Response<List<Comments>>

    suspend fun forgetPassword(email: String): Response<String>
}