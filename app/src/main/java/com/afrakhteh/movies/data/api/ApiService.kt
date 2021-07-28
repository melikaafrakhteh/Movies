package com.afrakhteh.movies.data.api

import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.util.consts.URLS
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //login
    @POST(URLS.LOGIN_URL)
    @FormUrlEncoded
    suspend fun login(
            @Field("email") email: String,
            @Field("password") password: String
    ): Response<String>

    //register
    @POST(URLS.REGISTER_URL)
    @FormUrlEncoded
    suspend fun register(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
    ): Response<String>

    //new movies
    @GET(URLS.GET_LIMIT_NEW_URL)
    suspend fun getLimitedNewMovie(): Response<List<Movies>>

    // all new movies
    @GET(URLS.GET_ALL_NEW_URL)
    suspend fun getAllNewMovie(): Response<List<Movies>>

    //popular movies
    @GET(URLS.GET_LIMIT_POPULAR_URL)
    suspend fun getLimitedPopular(): Response<List<Movies>>

    //all popular movies
    @GET(URLS.GET_ALL_POPULAR_URL)
    suspend fun getAllPopularMovie(): Response<List<Movies>>

    // get movies actors
    @FormUrlEncoded
    @POST(URLS.GET_CAST_URL)
    suspend fun getAllMoviesActors(
            @Field("id") movie_id: Int
    ): Response<List<Actors>>

    //search
    @GET(URLS.SEARCH_URL)
    suspend fun search(
            @Query("s")
            searchQuery: String,
    ): Response<Movies>

    //send comment
    @FormUrlEncoded
    @POST(URLS.SEND_COMMENT_URL)
    suspend fun sendComments(
        @Field("movie_id") movie_id: Int,
        @Field("email") email: String,
        @Field("main_comment") comment: String
    ): Response<String>

}