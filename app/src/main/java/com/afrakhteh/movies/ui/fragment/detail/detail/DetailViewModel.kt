package com.afrakhteh.movies.ui.fragment.detail.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MainRepository, private val networkHelper: NetworkHelper) : ViewModel() {

    val detail = MutableLiveData<Movies>()


    private val actor = MutableLiveData<Resource<List<Actors>>>()
    val actorList: LiveData<Resource<List<Actors>>> get() = actor


    private val save = MutableLiveData<Resource<String>>()
    val saveMovieItem: LiveData<Resource<String>> get() = save


    fun fetchData(
            id: Int, movie_name: String, director: String, image: String, trailers: String, new: Int, description: String, rate: Float
    ) {
        val movie = Movies(id, movie_name, director, image, trailers, new, description, rate)
        detail.value = movie
    }


    fun getActorList(id: Int) {
        viewModelScope.launch {
            actor.postValue(Resource.loading(null))
            repository.getAllMoviesActors(id).let { response ->
                if (response.isSuccessful) {
                    actor.postValue(Resource.success(response.body()))
                } else {
                    actor.postValue(Resource.error(response.errorBody().toString(), null))
                }
            }
        }
    }

    fun saveMovie(movie_id: Int, email: String, name: String, director: String, image: String, rate: String) {
        viewModelScope.launch {
            save.postValue(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                repository.saveMovie(movie_id, email, name, director, image, rate).let {
                    if (it.isSuccessful) {
                        if (it.body().equals("1")) {
                            save.postValue(Resource.success("Successfully added to saved!"))
                        } else {
                            save.postValue(Resource.error("try again", null))
                        }
                    } else {
                        save.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }

            } else {
                save.postValue(Resource.error("No Internet Connection Found.Check Your Connection", null))
            }
        }
    }
}