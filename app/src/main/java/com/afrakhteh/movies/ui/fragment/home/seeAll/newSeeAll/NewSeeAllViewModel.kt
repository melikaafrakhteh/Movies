package com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class NewSeeAllViewModel(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val allNew = MutableLiveData<Resource<List<Movies>>>()
    val allNewMovie: LiveData<Resource<List<Movies>>> get() = allNew

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            allNew.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getAllNewMovie().let { response ->
                    if (response.isSuccessful) {
                        allNew.postValue(Resource.success(response.body()))
                    } else {
                        allNew.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                allNew.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}