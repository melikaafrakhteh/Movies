package com.afrakhteh.movies.ui.fragment.signs.forgetPass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.FragmentForgetPassBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.nework.Status
import org.koin.android.ext.android.inject


class ForgetPassFragment : BaseFragment() {

    private lateinit var binding: FragmentForgetPassBinding
    private val viewModel: ForgetPasswordViewModel by inject()
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPassBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        successFul()
        buttonClick()
    }

    private fun buttonClick() {
        binding.forgetPassBackIv.setOnClickListener {
            val action = ForgetPassFragmentDirections.actionForgetPassFragmentToLoginFragment()
            navigate(action)
        }

        binding.forgetPassSendButtonTv.setOnClickListener {
            getEmailFromUser()
            if (checkEmailNotEmpty()){ // true
                observeViewModel()
            }else{
                binding.forgetPassGetEdt.error = getString(R.string.login_error_fill_empty_email)
            }

        }
    }

    private fun observeViewModel() {
        viewModel.forgetPassword(email)
        viewModel.forgetPassWord.observe(viewLifecycleOwner, Observer {
            response ->
            when(response.status){
                Status.LOADING -> {
                    loading()
                }
                Status.ERROR -> {
                    errors(response.message!!)
                }
                Status.SUCCESS -> {
                    successFul()
                    messageToast(response.data!!)
                }
            }
        })
    }

    private fun successFul() {
        binding.forgetPassProgressBar.visibility = View.GONE
        binding.forgetPassErrorTv.visibility = View.GONE

    }
    private fun loading(){
        binding.forgetPassProgressBar.visibility = View.VISIBLE
        binding.forgetPassErrorTv.visibility = View.GONE
    }
    private fun errors(msg: String) {
        binding.forgetPassProgressBar.visibility = View.GONE
        binding.forgetPassErrorTv.visibility = View.VISIBLE
        binding.forgetPassErrorTv.text = msg
    }

    private fun getEmailFromUser() {
        email = binding.forgetPassGetEdt.toString().trim()
    }

    private fun checkEmailNotEmpty(): Boolean{
        if (email.isEmpty()){
            return false
        }else{
            return true
        }
    }
}