package com.afrakhteh.movies.ui.fragment.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.data.dataModel.SaveModel
import com.afrakhteh.movies.databinding.FragmentSaveBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class SaveFragment : BaseFragment() {

    private lateinit var binding: FragmentSaveBinding
    private val viewModel: SaveViewModel by inject()

    private lateinit var saveAdapter: SaveAdapter
    private val list: ArrayList<SaveModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoadingFun()
        observeViewModel()
        implementRecycler()
        buttonClick()
    }

    private fun buttonClick() {
        binding.saveBackIv.setOnClickListener {
         val action = SaveFragmentDirections.actionSaveFragmentToHomeFragment()
            navigate(action)
        }
    }

    private fun implementRecycler() {
        saveAdapter = SaveAdapter(list, requireContext())
        binding.saveRecyclerRv.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = saveAdapter

        }
    }

    private fun observeViewModel() {
        viewModel.showAllList().observe(viewLifecycleOwner, Observer { save ->
            save?.let {
                saveAdapter.loadData(save)
                successFun()
            }
        })
    }

    private fun successFun() {
        binding.saveRecyclerRv.visibility = View.VISIBLE
        binding.saveProgress.visibility = View.GONE
    }

    private fun LoadingFun() {
        binding.saveRecyclerRv.visibility = View.GONE
        binding.saveProgress.visibility = View.VISIBLE
    }

}