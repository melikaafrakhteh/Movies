package com.afrakhteh.movies.ui.fragment.comments.NewComments

import androidx.lifecycle.ViewModel
import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.util.nework.NetworkHelper

class NewCommentViewModel (
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
   ): ViewModel(){

}