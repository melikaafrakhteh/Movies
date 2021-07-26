package com.afrakhteh.movies.ui.fragment.signs.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.FragmentRegisterBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.CONSTANTS
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.nework.Status
import com.afrakhteh.movies.util.storage.MyShared
import org.koin.android.ext.android.inject


class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by inject()

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerProgress.visibility = View.GONE

        buttonClick()

    }

    private fun buttonClick() {
        binding.registerBackIv.setOnClickListener {
            backToLogin()
        }
        binding.registerSignInTv.setOnClickListener {
            backToLogin()
        }
        binding.registerRegisterButtonTv.setOnClickListener {
            getUseName()
            if (name.isNotEmpty()) {
                getEmail()
                if (email.isNotEmpty()) {
                    getPassword()
                    if (checkPassword()) {
                        getConfirmPassWord()
                        if (validPassWordCheck()) {
                            sendInformationToServer()

                        } else {
                            binding.registerGetUserConfirmPass.error = getString(R.string.register_error_not_match)
                        }
                    } else {
                        binding.registerGetUserPassword.error = getString(R.string.register_error_min_number_pass)
                    }
                } else {
                    binding.registerGetUserEmail.error = getString(R.string.register_error_fill_empty_email)
                }
            } else {
                binding.registerGetUserName.error = getString(R.string.register_error_fill_empty_name)
            }
        }
    }

    private fun sendInformationToServer() {

        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.registerProgress.visibility = View.GONE
                    messageToast("${it.data}")
                    goToHome()
                    saveData()
                }
                Status.LOADING -> {
                    binding.registerProgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.registerProgress.visibility = View.GONE
                    messageToast(it.message!!)
                }
            }

        })

        viewModel.register(name, email, password)
    }

    private fun saveData() {
        MyShared.save(KEYS.SHARED_EMAIL, email)
        MyShared.save(KEYS.SHARED_NAME, name)
    }

    private fun goToHome() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
        navigate(action)
    }

    private fun validPassWordCheck(): Boolean {
        if (confirmPassword.isNotEmpty()) {
            val check = confirmPassword.equals(password)
            if (check) {
                return true
            } else {
                return false
            }
        }
        return false
    }

    private fun getConfirmPassWord() {
        confirmPassword = binding.registerGetUserConfirmPass.text.toString().trim()
    }

    private fun checkPassword(): Boolean {
        if (password.isNotEmpty()) {
            if (password.length >= CONSTANTS.PASSWORD_MIN_LENGTH) {
                return true
            } else return false
        }
        return false
    }

    private fun getPassword() {
        password = binding.registerGetUserPassword.text.toString().trim()
    }

    private fun getEmail() {
        email = binding.registerGetUserEmail.text.toString().trim()
    }

    private fun getUseName() {
        name = binding.registerGetUserName.text.toString().trim()
    }

    private fun backToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        navigate(action)
    }
}