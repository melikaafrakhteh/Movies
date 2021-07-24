package com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.PopularMovieLayoutBinding
import com.afrakhteh.movies.util.consts.KEYS
import com.bumptech.glide.Glide

class NewSeeAllAdapter(
    private val seeList: ArrayList<Movies>,
    private val context: Context
) : RecyclerView.Adapter<NewSeeAllAdapter.vH>() {

    fun addData(newList: List<Movies>) {
        seeList.clear()
        seeList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vH {
        val binding = PopularMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return vH(binding)
    }

    override fun onBindViewHolder(holder: vH, position: Int) {
        holder.bind(seeList[position], context)
    }

    override fun getItemCount(): Int = seeList.size

    class vH(private val binding: PopularMovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Movies, context: Context) {
            binding.layoutPopularNameTv.text = model.movie_name
            binding.layoutPopularDirectorNameTv.text = model.director
            binding.layoutPopularRateTv.text = model.rate.toString()
            Glide.with(context).load(model.image).into(binding.layoutPopularImageIv)

            binding.popularMoviesLayoutConstraint.setOnClickListener {
                fetchData(model)
            }
        }

        private fun fetchData(model: Movies) {
            val action = R.id.action_newSeeAllFragment_to_detailFragment
            val bundle = Bundle()

            bundle.putInt(KEYS.ID, model.id!!)
            bundle.putInt(KEYS.NEW, model.new!!)

            bundle.putFloat(KEYS.RATE, model.rate!!)

            bundle.putString(KEYS.NAME, model.movie_name)
            bundle.putString(KEYS.DECS, model.description)
            bundle.putString(KEYS.DIRECTOR, model.director)
            bundle.putString(KEYS.IMAGE, model.image)
            bundle.putString(KEYS.TRAILERS, model.trailers)

            itemView.findNavController().navigate(action, bundle)
        }
    }
}