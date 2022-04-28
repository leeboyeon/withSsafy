package com.ssafy.withssafy.src.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemMessageDetailTalkBinding
import com.ssafy.withssafy.src.dto.Message

class MessageDetailAdapter() : RecyclerView.Adapter<MessageDetailAdapter.DetailViewHolder>() {
    var list = mutableListOf<Message>()
    inner class DetailViewHolder(private val binding:ItemMessageDetailTalkBinding) : RecyclerView.ViewHolder(binding.root){
        var type = itemView.findViewById<TextView>(R.id.fragment_messageDetail_type)
        fun bind(message: Message){
            binding.message = message
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_message_detail_talk,parent,false))
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}