package com.afrakhteh.movies.ui.fragment.save

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.R
import com.afrakhteh.movies.data.dataModel.SaveModel
import com.afrakhteh.movies.databinding.PopularMovieLayoutBinding
import com.afrakhteh.movies.util.consts.KEYS
import com.bumptech.glide.Glide

class SaveAdapter(private val list: ArrayList<SaveModel>, private val context: Context)
    : RecyclerView.Adapter<SaveAdapter.viewHolder>() {

    fun loadData(newList: List<SaveModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val bind = PopularMovieLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(bind)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(list[position], context)
    }

    override fun getItemCount(): Int = list.size


    class viewHolder(private val binding: PopularMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(save: SaveModel, context: Context) {
            binding.layoutPopularNameTv.text = save.name
            binding.layoutPopularDirectorNameTv.text = save.director
            binding.layoutPopularRateTv.text = save.rate


            Glide.with(context).load(save.image).into(binding.layoutPopularImageIv)

            binding.popularMoviesLayoutConstraint.setOnClickListener {
                val action = R.id.action_saveFragment_to_detailFragment
                val bundle = Bundle()

                bundle.putInt(KEYS.ID, save.movie_id)
                bundle.putString(KEYS.NAME, save.name)
                bundle.putString(KEYS.DIRECTOR, save.director)
                bundle.putString(KEYS.RATE, save.rate)
                bundle.putString(KEYS.IMAGE, save.image)
                bundle.putString(KEYS.TRAILERS,save.trailers)
                bundle.putString(KEYS.DECS, save.description)
                bundle.putInt(KEYS.NEW, save.new!!)

                itemView.findNavController().navigate(action, bundle)
            }
        }

    }

    /*bundle.putInt(KEYS.SAVE_ID, save.isSaved)
    bundle.putInt(KEYS.SAVE_MOVIE_ID, save.movie_id)
    bundle.putString(KEYS.SAVE_NAME, save.name)
    bundle.putString(KEYS.SAVE_DIRECTOR, save.director)
    bundle.putString(KEYS.RATE, save.rate)
    bundle.putString(KEYS.SAVE_IMAGE, save.image)*/


}