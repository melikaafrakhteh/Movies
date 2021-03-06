package com.afrakhteh.movies.ui.fragment.signs.login

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.FragmentLoginBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.CONSTANTS
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.nework.Status
import com.afrakhteh.movies.util.storage.MyShared
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var email: String
    private lateinit var passWord: String

    private val loginViewModel: LoginViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginProgressBar.visibility = View.GONE


        onBackPressed()
        buttonClicks()
    }

    private fun onBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
                requireActivity(),
                onBackPressedCallback
        )
    }

    private fun getEmail() {
        email = binding.loginGetUserEmail.text.toString().trim() + ".com"
    }

    private fun getPassWord() {
        passWord = binding.loginGetUserPassword.text.toString().trim()
    }

    private fun checkPasswordIsValidation(): Boolean {
        return if (passWord.isEmpty()) {
            binding.loginGetUserPassword.error = getString(R.string.login_error_fill_empty_pass)
            false

        } else {
            passWord.length >= CONSTANTS.PASSWORD_MIN_LENGTH
        }
    }

    private fun buttonClicks() {
        binding.loginForgetPassTv.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToForgetPassFragment()
            navigate(action)
        }

        binding.loginSignUpTv.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navigate(action)

        }

        binding.loginSendButtonTv.setOnClickListener {
            getEmail()
            if (email.isEmpty()) {
                binding.loginGetUserEmail.error =
                        getString(R.string.login_error_fill_empty_email)
            } else {
                getPassWord()
                if (checkPasswordIsValidation()) { //true
                    checkNetwork()
                } else {
                    binding.loginGetUserPassword.error =
                            getString(R.string.login_error_min_number_pass)
                }
            }
        }
    }

    private fun checkNetwork() {
        if (checkInternet()) {
            //true
            sendInformationToServer()
            loginViewModel.login(email, passWord)
        } else {
            messageToast("No Internet connected")
        }
    }

    private fun sendInformationToServer() {

        loginViewModel.response.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loginProgressBar.visibility = View.GONE
                    binding.loginErrorTv.visibility = View.GONE
                    messageToast("welcome dear ${it.data}")
                    saveEmail(it.data)
                    goToHome()
                }
                Status.LOADING -> {
                    binding.loginProgressBar.visibility = View.VISIBLE
                    binding.loginErrorTv.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.loginProgressBar.visibility = View.GONE
                    binding.loginErrorTv.visibility = View.VISIBLE
                    binding.loginErrorTv.text = it.message
                }
            }
        })
    }

    private fun saveEmail(data: String?) {
        MyShared.getInstance(requireContext())
        MyShared.save(KEYS.SHARED_EMAIL,data!!)
        savePassword()
    }

    private fun savePassword() {
        MyShared.save(KEYS.SHARED_PASS,passWord)
    }

    private fun goToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        val handle = Handler()
        handle.postDelayed({
            navigate(action)
        }, 1000)
    }


}

