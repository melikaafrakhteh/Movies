package com.afrakhteh.movies.data.api

import androidx.lifecycle.MutableLiveData
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.util.nework.Resource
import retrofit2.Response

class ApiHelperImpl(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun login(email: String, password: String):
            MutableLiveData<String> {
        return apiService.login(email, password)
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


}