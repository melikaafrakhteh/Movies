package com.afrakhteh.movies.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

open class BaseFragment : Fragment() {

    fun showImages(uri: Int, img: ImageView) {
        Glide.with(requireContext()).load(uri).into(img)
    }

    fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    fun checkInternet(): Boolean {
        var isNetworkOn = false // think we are not connected to internet
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    isNetworkOn = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        isNetworkOn = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        isNetworkOn = true
                    }
                }
            }
        }
        //false ---> network is unavailable --->Dialog No Internet
        //true ---> network is available ---> Home or Login
        return isNetworkOn
    }

    fun messageToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}