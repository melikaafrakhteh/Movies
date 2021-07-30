package com.afrakhteh.movies.ui.fragment.comments.getAllComments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.movies.data.dataModel.Comments
import com.afrakhteh.movies.databinding.CommentItemLayoutBinding

class CommentsAdapter (private val commentList: ArrayList<Comments>, val context: Context)
    :RecyclerView.Adapter<CommentsAdapter.viewHolder>(){

    fun loadData(newList: List<Comments>) {
        commentList.clear()
        commentList.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = CommentItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding(commentList[position])
    }

    override fun getItemCount(): Int = commentList.size


    class viewHolder(private val bind: CommentItemLayoutBinding)
        : RecyclerView.ViewHolder(bind.root) {

        fun binding(comments: Comments) {
            bind.commentListCommentTv.text = comments.main_comment
            bind.commentListNameTv.text = comments.user_name
        }

    }
}