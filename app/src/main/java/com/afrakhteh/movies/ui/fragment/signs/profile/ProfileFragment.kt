package com.afrakhteh.movies.ui.fragment.signs.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.FragmentPofileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentPofileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPofileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}