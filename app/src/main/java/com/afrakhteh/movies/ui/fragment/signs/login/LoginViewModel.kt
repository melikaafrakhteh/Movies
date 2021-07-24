package com.afrakhteh.movies.ui.fragment.signs.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.consts.CONSTANTS
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
        private val repository: MainRepository,
        private val networkHelper: NetworkHelper
) : ViewModel() {

    private val loginResult: MutableLiveData<Resource<String>> = MutableLiveData()
    val response: LiveData<Resource<String>>
        get() = loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {

            loginResult.postValue(Resource.loading(null)) 

            if (networkHelper.isNetworkConnected()) {
                repository.login(email, password).let {
                    if (it.isSuccessful) {
                        loginResult.postValue(Resource.success(it.body()))
                        if (it.body() == CONSTANTS.ERROR_LOGIN) {
                            loginResult.postValue(Resource.error("You have not register yet", null))
                        } else {
                            loginResult.postValue(Resource.success(it.body()))
                        }
                    } else {
                        loginResult.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                loginResult.postValue(Resource.error("check your internet connection", null))
            }
        }
    }
}