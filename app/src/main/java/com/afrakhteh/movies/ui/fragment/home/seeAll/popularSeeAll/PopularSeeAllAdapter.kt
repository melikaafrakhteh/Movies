package com.afrakhteh.movies.ui.fragment.home.seeAll.popularSeeAll

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.PopularMovieLayoutBinding
import com.bumptech.glide.Glide

class PopularSeeAllAdapter(
    private val popList: ArrayList<Movies>,
    private val context: Context
) : RecyclerView.Adapter<PopularSeeAllAdapter.viewHolder>() {

    fun addData(newList: List<Movies>) {
        popList.clear()
        popList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding =
            PopularMovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(popList[position], context)
    }

    override fun getItemCount(): Int = popList.size


    class viewHolder(
        private val binding: PopularMovieLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Movies, context: Context) {
            binding.layoutPopularNameTv.text = model.movie_name
            binding.layoutPopularDirectorNameTv.text = model.director
            binding.layoutPopularRateTv.text = model.rate.toString()

            Glide.with(context).load(model.image).into(binding.layoutPopularImageIv)
        }
    }
}