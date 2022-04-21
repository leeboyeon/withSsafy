package com.ssafy.withssafy.src.main.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R

class NotiListAdapter() : RecyclerView.Adapter<NotiListAdapter.NotiListHolder>() {

    inner class NotiListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_noti_list_item, parent, false)
        return NotiListHolder(view)
    }

    override fun onBindViewHolder(holder: NotiListHolder, position: Int) {
        holder.apply {
            bind()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

}