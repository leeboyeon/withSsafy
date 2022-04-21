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
import java.util.*

class NotiListAdapter(val context: Context, private val items : ArrayList<Data>) : RecyclerView.Adapter<NotiListAdapter.NotiListHolder>() {

    inner class NotiListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item : Data) {

            itemView.findViewById<TextView>(R.id.rv_item_tv_noti_list_delete).setOnClickListener {
                removeData(this.layoutPosition)
                Toast.makeText(context, "삭제했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_noti_list_item, parent, false)
        return NotiListHolder(view)
    }

    override fun onBindViewHolder(holder: NotiListHolder, position: Int) {
        holder.apply {
            bind(items[position])
        }
    }

    fun removeData(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}