package com.afrakhteh.movies.ui.fragment.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.repository.DataBaseRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DataBaseRepository
) : ViewModel() {

    fun removeAllData(){
        viewModelScope.launch {
            repository.removeAllData()
        }
    }
}