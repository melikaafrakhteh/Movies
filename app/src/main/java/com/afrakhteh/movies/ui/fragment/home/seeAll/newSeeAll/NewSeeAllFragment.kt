package com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.FragmentNewSeeAllBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.nework.Status
import org.koin.android.ext.android.inject


class NewSeeAllFragment : BaseFragment() {

    private lateinit var binding: FragmentNewSeeAllBinding

    private val viewModel: NewSeeAllViewModel by inject()
    private lateinit var seeAllAdapter: NewSeeAllAdapter
    private val list: ArrayList<Movies> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewSeeAllBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClick()
        implementRecyclerView()
        viewModelObserver()
        buttonClick()
    }

    private fun viewModelObserver() {
        viewModel.allNewMovie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> {
                    error(it.message)
                }
                Status.LOADING -> {
                    loading()
                }
                Status.SUCCESS -> {
                    success()
                    it.data?.let {
                        renderAllNewMovieList(it)
                    }
                }
            }
        })

    }

    private fun implementRecyclerView() {
        seeAllAdapter = NewSeeAllAdapter(list, requireContext())
        binding.newSeeRv.apply {
            hasFixedSize()
            adapter = seeAllAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun buttonClick() {
        binding.newSeeBackIv.setOnClickListener {
            val action = NewSeeAllFragmentDirections.actionNewSeeAllFragmentToHomeFragment()
            navigate(action)
        }
    }

    private fun renderAllNewMovieList(all: List<Movies>) {
        seeAllAdapter.addData(all)
    }

    private fun loading() {
        binding.newSeeAllProgress.visibility = View.VISIBLE
        binding.newSeeRv.visibility = View.GONE
        binding.newSeeAllErrorTv.visibility = View.GONE
    }

    private fun error(msg: String?) {
        binding.newSeeRv.visibility = View.GONE
        binding.newSeeAllProgress.visibility = View.GONE
        binding.newSeeAllErrorTv.visibility = View.VISIBLE
        binding.newSeeAllErrorTv.text = msg

    }

    private fun success() {
        binding.newSeeAllProgress.visibility = View.GONE
        binding.newSeeRv.visibility = View.VISIBLE
        binding.newSeeAllErrorTv.visibility = View.GONE
    }
}