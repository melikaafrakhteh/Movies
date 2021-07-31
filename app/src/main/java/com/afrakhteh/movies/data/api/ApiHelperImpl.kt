package com.afrakhteh.movies.data.api

import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Comments
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.dataModel.Search
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

    override suspend fun search(search: String): Response<Search> {
        return apiService.search(search)
    }

    override suspend fun sendComments(movie_id: Int, name: String, comment: String): Response<String> {
        return apiService.sendComments(movie_id, name, comment)
    }

    override suspend fun getAllComments(movie_id: Int): Response<List<Comments>> {
        return apiService.getAllComments(movie_id)
    }

    override suspend fun forgetPassword(email: String): Response<String> {
        return apiService.forgetPassword(email)
    }


}