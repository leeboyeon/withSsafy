package com.ssafy.withssafy.src.main.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemMessageGroupBinding
import com.ssafy.withssafy.src.dto.Message

class MessageGroupAdapter  : RecyclerView.Adapter<MessageGroupAdapter.GroupViewHolder>(){
    var list = mutableListOf<Message>()
    inner class GroupViewHolder(private val binding:ItemMessageGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message){
            binding.message = message
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_message_group,parent,false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position,list[position].u_fromId)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface ItemClickListener{
        fun onClick(view: View, position:Int, fromId:Int)
    }
    private lateinit var itemClickListener : ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}