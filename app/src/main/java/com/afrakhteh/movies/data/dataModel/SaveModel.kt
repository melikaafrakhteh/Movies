package com.afrakhteh.movies.data.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afrakhteh.movies.util.consts.CONSTANTS


@Entity(tableName = CONSTANTS.DB_TABLE_NAME)
data class SaveModel(

    @ColumnInfo(name = "movieid_col")
    var movie_id: Int = 0,

    @ColumnInfo(name = "names_col")
    var name: String? = null,

    @ColumnInfo(name = "images_col")
    var image: String? = null,

    @ColumnInfo(name = "rates_col")
    var rate: String? = null,

    @ColumnInfo(name = "directors_col")
    var director: String? = null,

    var trailers: String? = null,

    var new: Int = 0,

    var description: String? = null

){
    @PrimaryKey(autoGenerate = true)
    var saveId: Int = 0
}
