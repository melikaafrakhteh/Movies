package com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.PopularMovieLayoutBinding
import com.bumptech.glide.Glide

class NewSeeAllAdapter(
    private val seeList: ArrayList<Movies>,
    private val context: Context
) : RecyclerView.Adapter<NewSeeAllAdapter.vH>() {

    fun addData(newList: List<Movies>){
        seeList.clear()
        seeList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vH {
        val binding = PopularMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return vH(binding)
    }

    override fun onBindViewHolder(holder: vH, position: Int) {
        holder.bind(seeList[position],context)
    }

    override fun getItemCount(): Int  = seeList.size

    class vH(private val binding: PopularMovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Movies, context: Context) {
            binding.layoutPopularNameTv.text = model.movie_name
            binding.layoutPopularDirectorNameTv.text = model.director
            binding.layoutPopularRateTv.text = model.rate.toString()
            Glide.with(context).load(model.image).into(binding.layoutPopularImageIv)
        }
    }
}