package com.afrakhteh.movies.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.dataModel.Search
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class SearchViewModel(
        private var repository: MainRepository,
        private var networkHelper: NetworkHelper
) : ViewModel() {

    private var search: MutableLiveData<Resource<Search>> = MutableLiveData()
    val searchResponse: LiveData<Resource<Search>> get() = search

    fun search(searching: String) {
        viewModelScope.launch {
            search.postValue(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                repository.search(searching).let {
                    if (it.isSuccessful) {
                        search.postValue(Resource.success(it.body()))
                    } else {
                        search.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                search.postValue(Resource.error("No Internet Connection Found.Check Your Connection", null))
            }

        }
    }

}