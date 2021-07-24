package com.afrakhteh.movies.ui.fragment.home.popular

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

class PopularLimitedMovieAdapter(var popularList: ArrayList<Movies>, var context: Context) :
    RecyclerView.Adapter<PopularLimitedMovieAdapter.viewHolder>() {

    fun addData(newList: List<Movies>) {
        popularList.clear()
        popularList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = PopularMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(popularList[position], context)
    }

    override fun getItemCount(): Int = popularList.size

    class viewHolder(private var binding: PopularMovieLayoutBinding) :
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
            val action = R.id.action_homeFragment_to_detailFragment
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