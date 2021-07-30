package com.afrakhteh.movies.ui.fragment.comments.getAllComments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.dataModel.Comments
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class CommentListViewModel(
        private val repository: MainRepository,
        private val networkHelper: NetworkHelper
) : ViewModel() {

    private val comment = MutableLiveData<Resource<List<Comments>>>()
    val commentResponse: LiveData<Resource<List<Comments>>> get() = comment

    fun getAllComments(movie_id: Int) {
        viewModelScope.launch {
            comment.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getAllComments(movie_id).let {
                    if (it.isSuccessful) {
                        comment.postValue(Resource.success(it.body()))
                    } else {
                        comment.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                comment.postValue(Resource.error("No Internet Connection Found.Check Your Connection", null))
            }
        }
    }
}