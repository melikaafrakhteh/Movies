package com.afrakhteh.movies.data.api

import androidx.lifecycle.MutableLiveData
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.util.consts.URLS
import com.afrakhteh.movies.util.nework.Resource
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //login
    @POST(URLS.LOGIN_URL)
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): MutableLiveData<String>

    //register

    //new movie
    @GET(URLS.GET_LIMIT_NEW_URL)
    suspend fun getLimitedNewMovie() : Response<List<Movies>>

    @GET(URLS.GET_ALL_NEW_URL)
    suspend fun getAllNewMovie(): Response<List<Movies>>

    @GET(URLS.GET_LIMIT_POPULAR_URL)
    suspend fun getLimitedPopular(): Response<List<Movies>>

    @GET(URLS.GET_ALL_POPULAR_URL)
    suspend fun getAllPopularMovie(): Response<List<Movies>>

    @FormUrlEncoded
    @POST(URLS.GET_CAST_URL)
    suspend fun getAllMoviesActors(
            @Field("id") movie_id: Int
    ): Response<List<Actors>>
}