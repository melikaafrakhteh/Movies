package com.afrakhteh.movies.ui.fragment.detail.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.data.dataModel.Actors
import com.afrakhteh.movies.databinding.ActorLayoutBinding
import com.bumptech.glide.Glide

class ActorAdapter(val list: ArrayList<Actors>, val context: Context)
    : RecyclerView.Adapter<ActorAdapter.viewHolder>() {

    fun loadData(newList: List<Actors>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ActorLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
        )
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(list[position], context)
    }

    override fun getItemCount(): Int = list.size

    class viewHolder(val binding: ActorLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Actors, context: Context) {
            binding.layoutActorNameTv.text = model.name
            binding.layoutActorRoleNameTv.text = model.role

            Glide.with(context).load(model.image).into(binding.layoutActorIv)
        }
    }
}