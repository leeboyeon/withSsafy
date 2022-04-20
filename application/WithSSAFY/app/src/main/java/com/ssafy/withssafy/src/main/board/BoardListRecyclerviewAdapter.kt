package com.ssafy.withssafy.src.main.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.animation.content.Content
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemBoardBinding

class BoardListRecyclerviewAdapter : RecyclerView.Adapter<BoardListRecyclerviewAdapter.ViewHolder>() {
    var list = mutableListOf<Int>()

    inner class ViewHolder(private val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind() {

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_board, parent, false))
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bind()
//           .setOnClickListener {
//                itemClickListener.onClick(it, Content, position)
//            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ItemClickListener{
        fun onClick(view: View, contentView: TextView, position: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}