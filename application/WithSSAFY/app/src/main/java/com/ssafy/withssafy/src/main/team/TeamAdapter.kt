package com.ssafy.withssafy.src.main.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemTeamBinding

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){
    var list = mutableListOf<String>()
    inner class TeamViewHolder(private val binding : ItemTeamBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(team:String){
//            binding.team = team
//            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_team,parent,false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}