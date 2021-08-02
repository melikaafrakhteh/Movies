package com.afrakhteh.movies.ui.fragment.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afrakhteh.movies.data.dataModel.SaveModel
import com.afrakhteh.movies.data.repository.DataBaseRepository

class SaveViewModel(
        private val repository: DataBaseRepository
) : ViewModel() {

  fun showAllList(): LiveData<List<SaveModel>> = repository.showAllSaveList()

}