package com.afrakhteh.movies.ui.fragment.search

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

class SearchAdapter(
        private val movie: ArrayList<Movies>,
        private val context: Context
) : RecyclerView.Adapter<SearchAdapter.viewHolder>() {

    fun loadData(newList: List<Movies>){
        movie.clear()
        movie.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = PopularMovieLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(movie[position], context)
    }

    override fun getItemCount(): Int = movie.size


    class viewHolder(
            var binding: PopularMovieLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movies: Movies, context: Context) {
            binding.popularMoviesLayoutConstraint.setOnClickListener {
                fetchData(movies)
            }
            binding.layoutPopularRateTv.text = movies.rate.toString()
            binding.layoutPopularDirectorNameTv.text = movies.director
            binding.layoutPopularNameTv.text = movies.movie_name

            Glide.with(context).load(movies.image).into(binding.layoutPopularImageIv)
        }

        private fun fetchData(model: Movies) {
            val action = R.id.action_searchFragment_to_detailFragment
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