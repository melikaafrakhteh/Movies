package com.afrakhteh.movies.ui.fragment.home.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.MenuHomeLayoutBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.ui.fragment.home.new.NewLimitedAdapter
import com.afrakhteh.movies.ui.fragment.home.new.NewLimitedMovieViewModel
import com.afrakhteh.movies.ui.fragment.home.popular.PopularLimitedMovieAdapter
import com.afrakhteh.movies.ui.fragment.home.popular.PopularLimitedMovieViewModel
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.nework.Status
import com.afrakhteh.movies.util.storage.MyShared
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment() {

    private lateinit var binding: MenuHomeLayoutBinding
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

        binding = MenuHomeLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //limitedNewMoviesViewModel = ViewModelProvider(this).get(NewLimitedMovieViewModel::class.java)
        MyShared.getInstance(requireContext())

        limitedNewMoviesRecycler()
        limitedPopularMovieRecycler()
        limitedNewMovieViewModel()
        limitedPopularMovieViewModels()
        buttonClicks()
        setMenuUserName()
        menuButtonClicks()

    }

    private fun setMenuUserName() {
        val email = MyShared.load(KEYS.SHARED_EMAIL)
        binding.navigation.menuUserNameTv.text = email
    }

    private fun menuButtonClicks() {
        binding.navigation.menuHomeLinear.setOnClickListener {
            closeDrawer()
        }
        binding.navigation.menuProfileLinear.setOnClickListener {
            closeDrawer()
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            navigate(action)
        }
        binding.navigation.menuSaveLinear.setOnClickListener {
            closeDrawer()
            val action = HomeFragmentDirections.actionHomeFragmentToSaveFragment()
            navigate(action)
        }
        binding.navigation.menuSearchLinear.setOnClickListener {
            closeDrawer()
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            navigate(action)
        }
        binding.navigation.menuExitLinear.setOnClickListener {
            MyShared.getInstance(requireContext())
            MyShared.clear()
            binding.navigation.menuUserNameTv.text = ""
        }
    }

    private fun closeDrawer() {
        binding.homeDrawerLayout.closeDrawer(Gravity.LEFT)
    }

    private fun buttonClicks() {
        binding.homeFragment.homeSearchTv.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            navigate(action)

        }
        binding.homeFragment.homeMenuIv.setOnClickListener {
            binding.homeDrawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.homeFragment.homeNewSeeAllTv.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNewSeeAllFragment()
            navigate(action)
        }
        binding.homeFragment.homePopularSeeAllTv.setOnClickListener {
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
        binding.homeFragment.homePopularRv.apply {
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
        binding.homeFragment.homeNewRv.apply {
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
        binding.homeFragment.homeProgress.visibility = View.VISIBLE
        binding.homeFragment.homeNewRv.visibility = View.GONE
        binding.homeFragment.homePopularRv.visibility = View.GONE
    }

    private fun error(msg: String?) {
        binding.homeFragment.homeNewRv.visibility = View.GONE
        binding.homeFragment.homePopularRv.visibility = View.GONE
        binding.homeFragment.homeProgress.visibility = View.GONE
        messageToast(msg.toString())
    }

    private fun success() {
        binding.homeFragment.homeProgress.visibility = View.GONE
        binding.homeFragment.homeNewRv.visibility = View.VISIBLE
        binding.homeFragment.homePopularRv.visibility = View.VISIBLE
    }
}