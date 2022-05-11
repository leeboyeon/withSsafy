package com.ssafy.withssafy.src.main.notification

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.RecyclerviewNotiListItemBinding
import com.ssafy.withssafy.src.dto.Notification
import com.ssafy.withssafy.src.main.board.JobAdapter
import java.util.*

class NotiListAdapter(val context: Context) : RecyclerView.Adapter<NotiListAdapter.NotiListHolder>() {
    var list = mutableListOf<Notification>()
    inner class NotiListHolder(private val binding: RecyclerviewNotiListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var notiIcon = binding.rvItemIvNotiListIcon
        fun bind(noti : Notification) {
            binding.noti = noti
            if(noti.type == 1) {
                notiIcon.setImageResource(R.drawable.alert)
            } else if(noti.type == 2) {
                notiIcon.setImageResource(R.drawable.user2)
            } else if(noti.type == 3) {
                notiIcon.setImageResource(R.drawable.work)
            }
            itemView.findViewById<TextView>(R.id.rv_item_tv_noti_list_delete).setOnClickListener {
                removeData(this.layoutPosition)
                Toast.makeText(context, "삭제했습니다.", Toast.LENGTH_SHORT).show()
            }
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiListAdapter.NotiListHolder {
        return NotiListHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.recyclerview_noti_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: NotiListHolder, position: Int) {
        holder.apply {
            bind(list[position])
        }
    }

    fun removeData(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}