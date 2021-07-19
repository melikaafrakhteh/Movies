package com.afrakhteh.movies.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.FragmentHomeBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.ui.fragment.home.new.NewLimitedAdapter
import com.afrakhteh.movies.ui.fragment.home.new.NewLimitedMovieViewModel
import com.afrakhteh.movies.ui.fragment.home.popular.PopularLimitedMovieAdapter
import com.afrakhteh.movies.ui.fragment.home.popular.PopularLimitedMovieViewModel
import com.afrakhteh.movies.util.nework.Status
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val limitedNewMoviesViewModel: NewLimitedMovieViewModel by inject()
    private lateinit var newLimitedAdapter: NewLimitedAdapter
    private var newList: ArrayList<Movies> = ArrayList()

    private val limitedPopularMovieViewModel: PopularLimitedMovieViewModel by inject()
    private lateinit var popularLimitedAdapter: PopularLimitedMovieAdapter
    private var popularList: ArrayList<Movies> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //limitedNewMoviesViewModel = ViewModelProvider(this).get(NewLimitedMovieViewModel::class.java)

        limitedNewMoviesRecycler()
        limitedPopularMovieRecycler()
        limitedNewMovieViewModel()
        limitedPopularMovieViewModels()
        buttonClicks()

    }

    private fun buttonClicks() {
        binding.homeSearchTv.setOnClickListener { }
        binding.homeMenuIv.setOnClickListener { }
        binding.homeNewSeeAllTv.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNewSeeAllFragment()
            navigate(action)
        }
        binding.homePopularSeeAllTv.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPopularSeeAllFragment()
            navigate(action)
        }
    }

    private fun limitedPopularMovieViewModels() {
        limitedPopularMovieViewModel.popularMovie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    success()
                    it.data?.let { data ->
                        renderPopularMovieList(data)
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

    private fun limitedPopularMovieRecycler() {
        popularLimitedAdapter = PopularLimitedMovieAdapter(popularList, requireContext())
        binding.homePopularRv.apply {
            hasFixedSize()
            adapter = popularLimitedAdapter
            popularLimitedAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun limitedNewMovieViewModel() {
        limitedNewMoviesViewModel.movieNew.observe(
            viewLifecycleOwner, Observer {
                when (it.status) {

                    Status.SUCCESS -> {
                        success()
                        it.data?.let { data ->
                            renderNewMovieList(data)
                        }
                    }
                    Status.LOADING -> {
                        loading()
                    }

                    Status.ERROR -> {
                        error(it.message)
                    }
                }
            }
        )
    }

    private fun limitedNewMoviesRecycler() {
        newLimitedAdapter = NewLimitedAdapter(newList, requireContext())
        binding.homeNewRv.apply {
            adapter = newLimitedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            newLimitedAdapter.notifyDataSetChanged()
        }
    }

    private fun renderNewMovieList(new: List<Movies>) {
        newLimitedAdapter.addData(new)
    }

    private fun renderPopularMovieList(pop: List<Movies>) {
        popularLimitedAdapter.addData(pop)
    }

    private fun loading() {
        binding.homeProgress.visibility = View.VISIBLE
        binding.homeNewRv.visibility = View.GONE
        binding.homePopularRv.visibility = View.GONE
    }

    private fun error(msg: String?) {
        binding.homeNewRv.visibility = View.GONE
        binding.homePopularRv.visibility = View.GONE
        binding.homeProgress.visibility = View.GONE
        messageToast(msg.toString())
    }

    private fun success() {
        binding.homeProgress.visibility = View.GONE
        binding.homeNewRv.visibility = View.VISIBLE
        binding.homePopularRv.visibility = View.VISIBLE
    }
}