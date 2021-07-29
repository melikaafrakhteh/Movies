package com.afrakhteh.movies.ui.fragment.search

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.FragmentSearchBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.nework.Status
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by inject()
    private lateinit var searchAdapter: SearchAdapter
    private val list: ArrayList<Movies> = ArrayList()
    private lateinit var search: String
    private var isListEmpty: Boolean = true

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchProgress.visibility = View.GONE

        implementRecycler()
        observerViewModel()
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.searchIconLinear.setOnClickListener {
            isListEmpty = list.isEmpty()
            if (isListEmpty) {
                search = binding.searchSearchEdt.text.toString().trim()
                viewModel.search(search)
                binding.searchSearchEdt.setText("")
            } else {
                list.clear()
                searchAdapter.notifyDataSetChanged()

                var handler = Handler()
                handler.postDelayed({
                    search = binding.searchSearchEdt.text.toString().trim()
                    viewModel.search(search)
                    binding.searchSearchEdt.setText("")
                }, 100)
            }

        }
        binding.searchBackIv.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
            navigate(action)
        }
    }

    private fun observerViewModel() {
        viewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> {
                    errors(it.message)
                }
                Status.LOADING -> {
                    loading()
                }
                Status.SUCCESS -> {
                    success()
                    it.data?.let {  data ->
                        loadList(data.records)
                    }
                }
            }
        })
    }

    private fun errors(msg: String?) {
        binding.searchProgress.visibility = View.GONE
        binding.searchRecycler.visibility = View.GONE
        messageToast(msg!!)

    }

    private fun success() {
        binding.searchProgress.visibility = View.GONE
        binding.searchRecycler.visibility = View.VISIBLE
    }

    private fun loading() {
        binding.searchProgress.visibility = View.VISIBLE
        binding.searchRecycler.visibility = View.GONE
    }

    private fun loadList(newList: List<Movies>) {
        searchAdapter.loadData(newList)
    }

    private fun implementRecycler() {
        searchAdapter = SearchAdapter(list, requireContext())
        binding.searchRecycler.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }
}