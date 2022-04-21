package com.ssafy.withssafy.src.main.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemJobBinding

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>(){
    var list = mutableListOf<String>()
    inner class JobViewHolder(private val binding:ItemJobBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_job,parent,false))
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.apply {
            bind()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}