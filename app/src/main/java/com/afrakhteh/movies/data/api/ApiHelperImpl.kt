package com.afrakhteh.movies.data.api

import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import retrofit2.Response

class ApiHelperImpl(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun login(email: String, password: String):
            Response<String> {
        return apiService.login(email, password)
    }
    override suspend fun register(name: String, email: String, password: String):
            Response<String> {
        return apiService.register(name,email,password)
    }
    override suspend fun getLimitedNewMovie(): Response<List<Movies>> =
        apiService.getLimitedNewMovie()

    override suspend fun getLimitedPopular(): Response<List<Movies>> {
        return apiService.getLimitedPopular()
    }

    override suspend fun getAllNewMovie(): Response<List<Movies>> {
        return apiService.getAllNewMovie()
    }

    override suspend fun getAllPopularMovie(): Response<List<Movies>> {
        return apiService.getAllPopularMovie()
    }

    override suspend fun getAllMoviesActors(movie_id: Int): Response<List<Actors>> {
        return apiService.getAllMoviesActors(movie_id)
    }

    override suspend fun search(search: String): Response<Movies> {
        return apiService.search(search)
    }

    override suspend fun sendComments(movie_id: Int, email: String, comment: String): Response<String> {
        return apiService.sendComments(movie_id, email, comment)
    }


}