package com.afrakhteh.movies.ui.fragment.comments.getAllComments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Comments
import com.afrakhteh.movies.databinding.FragmentCommentListBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.nework.Status
import org.koin.android.ext.android.inject


class CommentListFragment : BaseFragment() {

    private lateinit var binding: FragmentCommentListBinding
    private val viewModel: CommentListViewModel by inject()

    private lateinit var commentsAdapter: CommentsAdapter
    private val list: ArrayList<Comments> = ArrayList()
    private var movie_id: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentCommentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie_id = arguments?.getInt(KEYS.KEY_MOVIE_ID)!!
        commentsAdapter = CommentsAdapter(list, requireContext())
        binding.listCommentImageIv.visibility = View.GONE

        viewModel.getAllComments(movie_id)

        implementRecycler()
        viewModelObserver()
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.listCommentBackIv.setOnClickListener {
            val action = R.id.action_commentListFragment_to_homeFragment
            navigate(action)
        }
        binding.listCommentNewFab.setOnClickListener {
            val action = R.id.action_commentListFragment_to_newCommentsFragment
            val bundle = Bundle()
            bundle.putInt(KEYS.KEY_MOVIE_ID , movie_id)
            navigate(action,bundle)
        }
    }

    private fun viewModelObserver() {
        viewModel.commentResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errors(it.message.toString())
                }
                Status.LOADING -> {
                    loading()
                }
                Status.SUCCESS -> {
                    if (it.data?.size != 0) {
                        successComment()
                    } else {
                        successNoComment()
                    }
                    it.data?.let { data->
                        loadingData(data)
                    }
                }
            }
        })


    }

    private fun successNoComment() {
        binding.listCommentProgress.visibility = View.GONE
        binding.listCommentImageIv.visibility = View.VISIBLE
        binding.listCommentRecycler.visibility = View.GONE
    }

    private fun successComment() {
        binding.listCommentProgress.visibility = View.GONE
        binding.listCommentImageIv.visibility = View.GONE
        binding.listCommentRecycler.visibility = View.VISIBLE
    }

    private fun loading() {
        binding.listCommentProgress.visibility = View.VISIBLE
        binding.listCommentImageIv.visibility = View.GONE
        binding.listCommentRecycler.visibility = View.GONE
    }

    private fun errors(message: String) {
        binding.listCommentProgress.visibility = View.GONE
        binding.listCommentImageIv.visibility = View.GONE
        binding.listCommentRecycler.visibility = View.GONE
        messageToast(message)
    }

    private fun implementRecycler() {
        binding.listCommentRecycler.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentsAdapter
        }
    }

    fun loadingData(newList: List<Comments>){
        commentsAdapter.loadData(newList)
    }
}