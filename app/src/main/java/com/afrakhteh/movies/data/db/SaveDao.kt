package com.afrakhteh.movies.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afrakhteh.movies.data.dataModel.SaveModel

@Dao
interface SaveDao {

    //add one item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToSave(save: SaveModel): Long

    //delete one item
    //@Query("Delete from save_tb where saveId = :id")
    // suspend fun removeFromSave(id: Int)

    //delete one item
    @Query("Delete from saved_tb where movieid_col = :id")
    suspend fun removeFromSave(id: Int)

    //delete All items
    @Query("Delete from saved_tb")
    suspend fun removeAllData()

    //show All save list
    @Query("SELECT * FROM saved_tb")
    fun showAllSaveList(): LiveData<List<SaveModel>>




}