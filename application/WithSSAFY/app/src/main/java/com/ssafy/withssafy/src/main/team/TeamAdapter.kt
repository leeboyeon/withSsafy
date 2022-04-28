package com.ssafy.withssafy.src.main.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemTeamBinding
import com.ssafy.withssafy.src.dto.study.Study

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() , Filterable{
    var list = mutableListOf<Study>()
    var filteredList = list
    inner class TeamViewHolder(private val binding : ItemTeamBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(study: Study){
            binding.study = study
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_team,parent,false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.apply {
            bind(filteredList[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position,filteredList[position].id)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }
    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if(charString.isEmpty()){
                    list
                }else{
                    val filteringList = ArrayList<Study>()
                    for(item in list){
                        if(item.category.contains(charString))
                            filteringList.add(item)
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Study>
                notifyDataSetChanged()
            }

        }
    }
}