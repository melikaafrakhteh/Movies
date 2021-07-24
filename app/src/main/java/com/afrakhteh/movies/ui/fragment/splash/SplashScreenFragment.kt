package com.afrakhteh.movies.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afrakhteh.movies.databinding.FragmentSplashBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.CONSTANTS

class SplashScreenFragment : BaseFragment(){

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fullScreen()
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSplashScreen()
    }

    private fun fullScreen() {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun showSplashScreen() {
        val handler = Handler()
        handler.postDelayed({
         //   checkInternetForNavigate()
            /*val action =
             SplashScreenFragmentDirections.actionSplashFragmentToHomeFragment()
            navigate(action)*/
            val action = SplashScreenFragmentDirections.actionSplashFragmentToLoginFragment()
             navigate(action)

        }, CONSTANTS.SPLASH_SCREEN_TIME)
    }

    private fun checkInternetForNavigate() {
        if (checkInternet()) {
            // go to login
           // val action = SplashScreenFragmentDirections.actionSplashFragmentToLoginFragment()
           // navigate(action)

        } else {
            // show no internet
          //  val action =
               // SplashScreenFragmentDirections.actionSplashScreenFragmentToNoInternetFragment()
           // navigate(action)
        }
    }

}