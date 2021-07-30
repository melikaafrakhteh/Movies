package com.afrakhteh.movies.data.dataModel

data class Comments(
        val id: Int = 0,
        val user_name: String? = "",
        val main_comment: String? = "",
        val confirm: Int = 0,
        val movie_id: Int = 0
)
