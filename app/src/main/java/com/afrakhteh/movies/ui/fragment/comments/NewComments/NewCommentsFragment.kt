package com.afrakhteh.movies.ui.fragment.comments.NewComments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.afrakhteh.movies.databinding.FragmentNewCommentsBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.nework.Status
import com.afrakhteh.movies.util.storage.MyShared
import org.koin.android.ext.android.inject

class NewCommentsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewCommentsBinding
    private val viewModel: NewCommentViewModel by inject()
    private lateinit var comment: String

    private lateinit var name: String
    private var movie_id: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewCommentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MyShared.getInstance(requireContext())
        name = MyShared.load(KEYS.SHARED_NAME)

        movie_id = arguments?.getInt(KEYS.KEY_MOVIE_ID)!!

        success()
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.newCommentBackIv.setOnClickListener {
            goToHome()
        }
        binding.newCommentSendButtonTv.setOnClickListener {
            getComment()
            viewModelObserver()
        }
    }

    private fun goToHome() {
       val action = NewCommentsFragmentDirections.actionNewCommentsFragmentToHomeFragment()
        navigate(action)
    }

    private fun viewModelObserver() {
        viewModel.commentResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    success()
                    messageToast(it.data!!)
                    val handler = Handler()
                    handler.postDelayed({ goToHome()  }, 1000)
                }
                Status.LOADING -> {
                    loading()
                }
                Status.ERROR -> {
                    errors(it.message!!)
                }
            }
        })
        viewModel.sendNewComment(movie_id, name, comment)

    }

    private fun success() {
        binding.newCommentProgressBar.visibility = View.GONE
        binding.newCommentErrorTv.visibility = View.GONE
    }

    private fun loading() {
        binding.newCommentProgressBar.visibility = View.VISIBLE
        binding.newCommentErrorTv.visibility = View.GONE
    }

    private fun errors(msg: String) {
        binding.newCommentProgressBar.visibility = View.GONE
        binding.newCommentErrorTv.visibility = View.VISIBLE
        binding.newCommentErrorTv.text = msg
    }

    private fun getComment() {
        comment = binding.newCommentGetEdt.text.toString().trim()
    }
}