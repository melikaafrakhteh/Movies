package com.afrakhteh.movies.ui.fragment.home.seeAll.popularSeeAll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.FragmentPopularSeeAllBinding
import com.afrakhteh.movies.databinding.PopularMovieLayoutBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.nework.Status
import org.koin.android.ext.android.inject

class PopularSeeAllFragment : BaseFragment() {

    private lateinit var binding: FragmentPopularSeeAllBinding
    private lateinit var popAdapter: PopularSeeAllAdapter
    private val list: ArrayList<Movies> = ArrayList()
    private val viewModel: PopularSeeAllViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularSeeAllBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        implementRecyclerView()
        viewModelObserver()
        buttonClick()
    }

    private fun buttonClick() {
        binding.popSeeBackIv.setOnClickListener {
            val action = PopularSeeAllFragmentDirections.actionPopularSeeAllFragmentToHomeFragment()
            navigate(action)
        }
    }

    private fun viewModelObserver() {
        viewModel.popAllMovie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    success()
                    it.data?.let { data ->
                        renderAllPopMovieList(data)
                    }
                }
                Status.LOADING -> {
                    loading()
                }
                Status.ERROR -> {
                    error(it.message)
                }
            }
        })

    }

    private fun renderAllPopMovieList(all: List<Movies>) {
        popAdapter.addData(all)
    }

    private fun loading() {
        binding.popSeeAllProgress.visibility = View.VISIBLE
        binding.popSeeRv.visibility = View.GONE
        binding.popSeeAllErrorTv.visibility = View.GONE
    }

    private fun error(msg: String?) {
        binding.popSeeRv.visibility = View.GONE
        binding.popSeeAllProgress.visibility = View.GONE
        binding.popSeeAllErrorTv.visibility = View.VISIBLE
        binding.popSeeAllErrorTv.text = msg

    }

    private fun success() {
        binding.popSeeAllProgress.visibility = View.GONE
        binding.popSeeRv.visibility = View.VISIBLE
        binding.popSeeAllErrorTv.visibility = View.GONE
    }

    private fun implementRecyclerView() {
        popAdapter = PopularSeeAllAdapter(list, requireContext())
        binding.popSeeRv.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = popAdapter
        }
    }
}