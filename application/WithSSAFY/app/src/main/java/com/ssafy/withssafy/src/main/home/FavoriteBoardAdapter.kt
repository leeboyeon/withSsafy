package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R

class FavoriteBoardAdapter() : RecyclerView.Adapter<FavoriteBoardAdapter.FavoriteBoardHolder>() {

    inner class FavoriteBoardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBoardHolder {
        return FavoriteBoardHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recyclerview_favorite_board_item, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteBoardHolder, position: Int) {
        holder.apply {
            bind()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

}