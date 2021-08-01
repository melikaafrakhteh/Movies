package com.afrakhteh.movies.ui.fragment.detail.detail

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.databinding.FragmentDetailBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.CONSTANTS
import com.afrakhteh.movies.util.consts.KEYS
import com.afrakhteh.movies.util.nework.Status
import com.afrakhteh.movies.util.storage.MyShared
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import org.koin.android.ext.android.inject

class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding
    var movieId = 0

    private lateinit var name: String
    private lateinit var director: String
    private lateinit var image: String
    private lateinit var trailers: String
    private lateinit var description: String
    private var new: Int = 0
    private var rate: Float = 0f

    private val viewModel: DetailViewModel by inject()

    private var isDescCompleted: Boolean = true

    private lateinit var castAdapter: ActorAdapter
    private val list: ArrayList<Actors> = ArrayList()

    private var isNotSaved: Boolean = true

    private lateinit var email: String
    private var imageInt: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundles()

        viewModel.fetchData(movieId, name, director, image, trailers, new, description, rate)
        viewModel.getActorList(movieId)

        MyShared.getInstance(requireContext())
        checkSaveButtonState()

        observeViewModel()

        buttonClick()

        implementCastRecycler()

        getEmail()
    }

    private fun checkSaveButtonState() {
        var saveDetail:Int = MyShared.loadInt(KEYS.SHARED_IMAGE)
        if (saveDetail!= 0){
            binding.detailSaveBtnIv.setImageResource(R.drawable.save)
        }else{
            binding.detailSaveBtnIv.setImageResource(R.drawable.notsave)
        }

    }

    private fun getEmail() {
        email = MyShared.load(KEYS.SHARED_EMAIL)
    }

    private fun implementCastRecycler() {
        castAdapter = ActorAdapter(list, requireContext())
        binding.detailCastRv.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun buttonClick() {
        binding.detailBackIv.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            navigate(action)
        }

        binding.detailSeeMoreTv.setOnClickListener {
            descriptionLimit()
        }

        binding.detailTrailerTv.setOnClickListener {
            val checkTrailer = trailers.equals(CONSTANTS.NO_TRAILER_EXISTS)
            if (checkTrailer) { //true -> no video
                messageToast("No Trailers Exists")
            } else {
                val action = R.id.action_detailFragment_to_trailersFragment
                val bundle = Bundle()
                bundle.putString(KEYS.TRAILERS, trailers)
                navigate(action, bundle)
            }
        }
        binding.detailCommentsBtnTv.setOnClickListener {
            val action = R.id.action_detailFragment_to_commentListFragment
            val bundle = Bundle()
            bundle.putInt(KEYS.KEY_MOVIE_ID, movieId)
            navigate(action, bundle)
        }

        binding.detailSaveBtnIv.setOnClickListener {
            if (isNotSaved) {
                changeSaveBtnAppearance()
                sendSaveInformationToServer()
                isNotSaved = false
            } else {
                defaultSaveBtnAppearance()
                isNotSaved = true
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    private fun defaultSaveBtnAppearance() {
        binding.detailSaveBtnIv.apply {
            imageInt = R.drawable.notsave
            setImageResource(imageInt)
            MyShared.deleteInt(KEYS.SHARED_IMAGE)
        }
    }

    private fun sendSaveInformationToServer() {
        viewModel.saveMovie(movieId, email, name, director, image, rate.toString())
    }

    @SuppressLint("ResourceAsColor")
    private fun changeSaveBtnAppearance() {
        binding.detailSaveBtnIv.apply {
            imageInt = R.drawable.save
            setImageResource(imageInt)
            MyShared.saveInt(KEYS.SHARED_IMAGE,imageInt)
        }
    }

    private fun observeViewModel() {

        viewModel.detail.observe(viewLifecycleOwner, Observer { model ->
            model?.let {
                addDataToViews()
            }
        })

        viewModel.actorList.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                when (response.status) {
                    Status.ERROR -> {
                        messageToast(response.message.toString())
                        binding.detailCastRv.visibility = View.GONE
                        binding.detailProgress.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.detailCastRv.visibility = View.GONE
                        binding.detailProgress.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.detailCastRv.visibility = View.VISIBLE
                        binding.detailProgress.visibility = View.GONE
                        response.data?.let {
                            castAdapter.loadData(it)
                        }

                    }
                }
            }
        })

        viewModel.saveMovieItem.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.detailProgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.detailProgress.visibility = View.GONE
                    messageToast(it.data!!)
                }
                Status.SUCCESS -> {
                    binding.detailProgress.visibility = View.GONE
                    it.data?.let {
                        messageToast(it)
                    }
                }
            }
        })
    }


    private fun addDataToViews() {
        binding.detailMovieNameTv.text = name
        binding.detailRateTv.text = rate.toString()
        binding.detailDirectorNameTv.text = director
        binding.detailDescTv.text = description

        Glide.with(requireContext()).load(image).into(binding.detailSmallImageIv)
        Glide.with(requireContext()).load(image)
                .apply(bitmapTransform(BlurTransformation(16)))
                .into(binding.detailBigImageIv)
    }

    private fun descriptionLimit() {
        if (isDescCompleted) {
            //true -->> shows whole text
            binding.detailDescTv.isSingleLine = false
            binding.detailSeeMoreTv.text = getString(R.string.detail_see_more_close_txt)
            isDescCompleted = false
        } else {
            //false -->> shows only one line
            binding.detailDescTv.isSingleLine = true
            binding.detailSeeMoreTv.text = getString(R.string.detail_see_more)
            isDescCompleted = true
        }

    }

    private fun getBundles() {
        movieId = arguments?.getInt(KEYS.ID)!!
        name = arguments?.getString(KEYS.NAME)!!
        director = arguments?.getString(KEYS.DIRECTOR)!!
        image = arguments?.getString(KEYS.IMAGE)!!
        trailers = arguments?.getString(KEYS.TRAILERS)!!
        description = arguments?.getString(KEYS.DECS)!!
        new = arguments?.getInt(KEYS.NEW)!!
        rate = arguments?.getFloat(KEYS.RATE)!!
    }
}