package com.afrakhteh.movies.ui.fragment.signs.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.consts.CONSTANTS
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(
        private var repository: MainRepository,
        private var networkHelper: NetworkHelper
) : ViewModel() {

    private val register: MutableLiveData<Resource<String>> = MutableLiveData()
    val response: LiveData<Resource<String>> get() = register

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {

            register.postValue(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                repository.register(name, email, password).let { response ->
                    if (response.isSuccessful) {
                        if (response.body().equals(CONSTANTS.REGISTER_HAVE_ACCOUNT)) {
                            register.postValue(Resource.error("This username already exists", null))
                        } else if(response.body().equals(CONSTANTS.REGISTER_SUCCESS_ACCOUNT)) {
                            register.postValue(Resource.success("Thank you for registering"))
                        }

                    } else {
                        register.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }

            } else {
                register.postValue(Resource.error("No Internet Connection Found.Check Your Connection", null))
            }
        }
    }
}