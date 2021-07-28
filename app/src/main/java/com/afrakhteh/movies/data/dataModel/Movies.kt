package com.afrakhteh.movies.data.dataModel

data class Movies(
    val id: Int? = 0,
    val movie_name: String? = null,
    val director: String? = null,
    val image: String? = null,
    val trailers: String? = null,
    val new: Int? = 0,
    val description : String = "",
    val rate: Float? = 0.0f
)
