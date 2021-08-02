package com.afrakhteh.movies.data.repository

import androidx.lifecycle.LiveData
import com.afrakhteh.movies.data.dataModel.SaveModel
import com.afrakhteh.movies.data.db.SaveDataBase

class DataBaseRepository(
    private val db: SaveDataBase
) {
    suspend fun addToSave(save: SaveModel): Long = db.dao().addToSave(save)
    suspend fun removeFromSave(id: Int) = db.dao().removeFromSave(id)
    suspend fun removeAllData() = db.dao().removeAllData()
    fun showAllSaveList(): LiveData<List<SaveModel>> = db.dao().showAllSaveList()
}