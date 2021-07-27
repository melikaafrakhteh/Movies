package com.afrakhteh.movies.ui.fragment.signs.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.FragmentPofileBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.storage.MyShared
import com.bumptech.glide.Glide


class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentPofileBinding

    private lateinit var email: String
    private lateinit var name: String
    private lateinit var password: String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPofileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MyShared.getInstance(requireContext())
        errorVisibility()

        checkProfileData()
        backButtonClick()
    }

    private fun backButtonClick() {
        binding.profileBackIv.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
            navigate(action)
        }

    }

    private fun errorVisibility() {
        binding.profileErrorNotRegisterTv.visibility = View.GONE
        binding.profileErrorGoRegisterTv.visibility = View.GONE
    }

    private fun checkProfileData() {
        loadData()
        val isRegistered = email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()
        if (isRegistered) { // true -> The user has registered
            showData()
            buttonClicks()
        } else {
            showNotRegister()
        }
    }

    private fun buttonClicks() {
        binding.profileCommentButtonTv.setOnClickListener {
            //todo go to comments fragment
        }
    }

    private fun showNotRegister() {
        notRegisterVisibilities()
        notRegisterInVisibility()
        notRegisterClicks()
    }

    private fun notRegisterClicks() {
        binding.profileCommentButtonTv.setOnClickListener {
            messageToast(getString(R.string.profile_not_register))
        }
        binding.profileErrorGoRegisterTv.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToRegisterFragment()
            navigate(action)
        }

    }

    private fun notRegisterInVisibility() {
        binding.profileEmailLinear.visibility = View.GONE
        binding.profileNameLinear.visibility = View.GONE
        binding.profilePassLinear.visibility = View.GONE
    }

    private fun notRegisterVisibilities() {
        binding.profileErrorNotRegisterTv.visibility = View.VISIBLE
        binding.profileErrorGoRegisterTv.visibility = View.VISIBLE
    }

    private fun showData() {
        binding.profileShowUserName.text = name
        binding.profileShowEmail.text = email
        binding.profileShowPassword.text = password
        notNullVisibilites()
    }

    private fun notNullVisibilites() {
        errorVisibility()
    }

    private fun loadData() {
        email = MyShared.load(KEYS.SHARED_EMAIL)
        password = MyShared.load(KEYS.SHARED_PASS)
        name = MyShared.load(KEYS.SHARED_NAME)
    }


}