package com.afrakhteh.movies.ui.fragment.home.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.PopularMovieLayoutBinding
import com.bumptech.glide.Glide

class PopularLimitedMovieAdapter(var popularList: ArrayList<Movies>, var context: Context)
    : RecyclerView.Adapter<PopularLimitedMovieAdapter.viewHolder>() {

    fun addData(newList: List<Movies>){
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

    class viewHolder(private var binding: PopularMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Movies, context: Context) {
            binding.layoutPopularNameTv.text = model.movie_name
            binding.layoutPopularDirectorNameTv.text = model.director
            binding.layoutPopularRateTv.text = model.rate.toString()
            Glide.with(context).load(model.image).into(binding.layoutPopularImageIv)
        }
    }


}