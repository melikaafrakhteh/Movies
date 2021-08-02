package com.afrakhteh.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afrakhteh.movies.data.dataModel.SaveModel
import com.afrakhteh.movies.util.consts.CONSTANTS

@Database(entities = [SaveModel::class],version = CONSTANTS.DB_DATABASE_VER )
abstract class SaveDataBase: RoomDatabase() {
    abstract fun dao(): SaveDao
}