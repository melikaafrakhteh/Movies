package com.afrakhteh.movies.ui.fragment.home.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class PopularLimitedMovieViewModel
(
        var repository: MainRepository,
        var networkHelper: NetworkHelper
) : ViewModel() {

    private val popular = MutableLiveData<Resource<List<Movies>>>()
    val popularMovie: LiveData<Resource<List<Movies>>>
        get() = popular

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            popular.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) { //true -> connected
                repository.getLimitedPopular().let { response ->

                    if (response.isSuccessful) {
                        popular.postValue(Resource.success(response.body()))
                    } else {
                        popular.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                popular.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}