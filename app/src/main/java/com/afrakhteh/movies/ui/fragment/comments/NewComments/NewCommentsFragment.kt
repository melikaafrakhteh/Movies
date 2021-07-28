package com.afrakhteh.movies.ui.fragment.comments.NewComments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.FragmentNewCommentsBinding
import com.afrakhteh.movies.ui.base.BaseFragment

class NewCommentsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewCommentsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewCommentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}