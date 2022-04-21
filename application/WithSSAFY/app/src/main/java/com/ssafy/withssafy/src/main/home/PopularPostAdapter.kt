package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R

class PopularPostAdapter() : RecyclerView.Adapter<PopularPostAdapter.PopularPostdHolder>() {

    inner class PopularPostdHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPostdHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_popular_item, parent, false)
        return PopularPostdHolder(view)
    }

    override fun onBindViewHolder(holder: PopularPostdHolder, position: Int) {
        holder.apply {
            bind()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

}