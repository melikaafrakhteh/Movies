package com.afrakhteh.movies.ui.fragment.detail.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.data.dataModel.SaveModel
import com.afrakhteh.movies.data.repository.DataBaseRepository
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MainRepository,
                      private val repo: DataBaseRepository) : ViewModel() {

    val detail = MutableLiveData<Movies>()


    private val actor = MutableLiveData<Resource<List<Actors>>>()
    val actorList: LiveData<Resource<List<Actors>>> get() = actor


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

    fun addToSave(save: SaveModel){
        viewModelScope.launch {
            repo.addToSave(save)
        }
    }

    fun removeFromSave(id: Int){
        viewModelScope.launch {
            repo.removeFromSave(id)
        }
    }

    fun showAllSaveList(): LiveData<List<SaveModel>> =  repo.showAllSaveList()




}