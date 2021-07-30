package com.afrakhteh.movies.ui.fragment.comments.NewComments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewCommentViewModel(
        private val repository: MainRepository,
        private val networkHelper: NetworkHelper
) : ViewModel() {

    private val comments: MutableLiveData<Resource<String>> = MutableLiveData()
    val commentResponse: LiveData<Resource<String>> get() = comments

    fun sendNewComment(movie_id: Int, name: String, comment: String) {
        viewModelScope.launch {
            comments.postValue(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                repository.sendComments(movie_id, name, comment).let { response ->
                    if (response.isSuccessful) {
                        if(response.body().equals("1"))
                        comments.postValue(Resource.success("Your comment was successfully submitted"))
                    } else {
                        comments.postValue(Resource.error(response.errorBody()?.toString(), null))
                    }
                }
            } else {
                comments.postValue(Resource.error("No Internet Connection Found.Check Your Connection", null))
            }
        }
    }

}