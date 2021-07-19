package com.afrakhteh.movies.ui.fragment.signs.login

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

    val loginResult: MutableLiveData<Resource<String>> = MutableLiveData()
    var response = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {

            loginResult.value = Resource.loading(null)

            if (networkHelper.isNetworkConnected()) {
                    response = repository.login(email, password)
                if (response.equals(CONSTANTS.ERROR_LOGIN)) {
                    loginResult.value = Resource.error("You have not register yet", null)
                } else {
                    loginResult.value = Resource.success(response.toString())
                }
            }else{
                loginResult.value = Resource.error("check your internet connection", null)
            }
        }
    }
}