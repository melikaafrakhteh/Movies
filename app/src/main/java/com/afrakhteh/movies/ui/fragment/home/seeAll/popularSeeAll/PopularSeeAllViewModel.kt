package com.afrakhteh.movies.ui.fragment.home.seeAll.popularSeeAll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class PopularSeeAllViewModel(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val pop = MutableLiveData<Resource<List<Movies>>>()
    val popAllMovie: LiveData<Resource<List<Movies>>> get() = pop

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            pop.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()){
                repository.getAllPopularMovie().let { response ->
                    if (response.isSuccessful){
                        pop.postValue(Resource.success(response.body()))
                    }else{
                        pop.postValue(Resource.error(response.errorBody().toString(),null))
                    }
                }
            }else{
                pop.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}