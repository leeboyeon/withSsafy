package com.ssafy.withssafy.src.main.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemJobBinding
import com.ssafy.withssafy.src.dto.Recruit

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>(){
    var list = mutableListOf<Recruit>()
    inner class JobViewHolder(private val binding:ItemJobBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(recruit : Recruit){
            binding.recruit = recruit
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_job,parent,false))
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position,list[position].id)
            }
        }
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }
}