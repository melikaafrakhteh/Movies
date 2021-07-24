package com.afrakhteh.movies.ui.fragment.home.new

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.NewMovieLayoutBinding
import com.afrakhteh.movies.util.consts.KEYS
import com.bumptech.glide.Glide

class NewLimitedAdapter(var newMovieList: ArrayList<Movies>, val context: Context) :
    RecyclerView.Adapter<NewLimitedAdapter.viewHolder>() {

    fun addData(newList: List<Movies>) {
        newMovieList.clear()
        newMovieList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val binding =
            NewMovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(newMovieList[position], context)
    }

    override fun getItemCount(): Int = newMovieList.size

    class viewHolder(private val binding: NewMovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Movies, context: Context) {
            binding.layoutNewNameTv.text = model.movie_name
            Glide.with(context).load(model.image).into(binding.layoutNewImageIv)
            binding.newMoviesLayoutLinear.setOnClickListener {
                fetchData(model)
            }

        }

        private fun fetchData(model: Movies) {
            val action = R.id.action_homeFragment_to_detailFragment
            val bundle = Bundle()

            bundle.putInt(KEYS.ID, model.id!!)
            bundle.putString(KEYS.NAME, model.movie_name)
            bundle.putString(KEYS.DIRECTOR, model.director)
            bundle.putString(KEYS.DECS, model.description)
            bundle.putString(KEYS.IMAGE, model.image)
            bundle.putString(KEYS.TRAILERS, model.trailers)
            bundle.putInt(KEYS.NEW, model.new!!)
            bundle.putFloat(KEYS.RATE, model.rate!!)

            itemView.findNavController().navigate(action, bundle)

        }


    }

}