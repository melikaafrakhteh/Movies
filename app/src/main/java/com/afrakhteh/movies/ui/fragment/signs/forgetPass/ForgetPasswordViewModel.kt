package com.afrakhteh.movies.ui.fragment.signs.forgetPass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.afrakhteh.movies.util.nework.Resource
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
        private val repository: MainRepository,
        private val networkHelper: NetworkHelper
) : ViewModel() {

    private val forget = MutableLiveData<Resource<String>>()
    val forgetPassWord: LiveData<Resource<String>> get() = forget

    fun forgetPassword(email: String) {
        viewModelScope.launch {
            forget.postValue(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                repository.forgetPassword(email).let {
                    if (it.isSuccessful) {
                        if (it.body().equals("1")) {
                            forget.postValue(Resource.success("Password sent to your email"))
                        } else {
                            forget.postValue(Resource.error("There is an error sending the password", null))
                        }

                    } else {
                        forget.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                forget.postValue(Resource.error("No Internet Connection Found.Check Your Connection", null))
            }
        }

    }
}