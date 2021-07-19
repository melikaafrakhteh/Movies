package com.afrakhteh.movies.data.dataModel

import com.squareup.moshi.Json

data class User(
    @Json(name = "id")
    val id: Int,

    @Json(name ="name")
    val name: String,

    @Json(name = " email")
    val email: String,

    @Json(name = "password")
    val password: String
)
