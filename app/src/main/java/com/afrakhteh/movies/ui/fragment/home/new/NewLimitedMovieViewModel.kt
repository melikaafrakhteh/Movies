package com.afrakhteh.movies.ui.fragment.home.new

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class NewLimitedMovieViewModel(
         val repo: MainRepository,
         val networkHelper: NetworkHelper
) : ViewModel() {



    private val movie = MutableLiveData<Resource<List<Movies>>>()
    val movieNew: LiveData<Resource<List<Movies>>> get() = movie

    init {

        fetchLimitedNewMovie()
    }

    private fun fetchLimitedNewMovie() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                //true ->connected to internet
                movie.postValue(Resource.loading(null))
                repo.getLimitedNewMovie().let { response ->
                    if (response.isSuccessful) {
                        movie.postValue(Resource.success(response.body()))
                    }else{
                        movie.postValue(Resource.error(response.errorBody().toString(),null))
                    }
                }
           }else{
                movie.postValue(Resource.error("No internet connection",null))
            }
        }
    }
}

