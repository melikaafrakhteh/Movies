package com.afrakhteh.movies.ui.fragment.home.new

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.data.dataModel.Movies
import com.afrakhteh.movies.databinding.NewMovieLayoutBinding
import com.bumptech.glide.Glide

class NewLimitedAdapter(var newMovieList: ArrayList<Movies>, val context: Context) :
        RecyclerView.Adapter<NewLimitedAdapter.viewHolder>() {

    fun addData(newList: List<Movies>) {
        newMovieList.clear()
        newMovieList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val binding = NewMovieLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(newMovieList[position], context)
    }

    override fun getItemCount(): Int = newMovieList.size

    class viewHolder(private val binding: NewMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Movies, context: Context) {
            binding.layoutNewNameTv.text = model.movie_name
            Glide.with(context).load(model.image).into(binding.layoutNewImageIv)

        }


    }

}