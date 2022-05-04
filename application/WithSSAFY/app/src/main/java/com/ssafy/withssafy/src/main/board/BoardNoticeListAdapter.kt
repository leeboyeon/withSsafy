package com.ssafy.withssafy.src.main.board

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemNoticeListBinding
import com.ssafy.withssafy.src.dto.notice.Notice

private const val TAG = "BoardNoticeListAdapter"
class BoardNoticeListAdapter : RecyclerView.Adapter<BoardNoticeListAdapter.BoardNoticeListViewHolder>(), Filterable {
    var list = mutableListOf<Notice>()
    var filteredList = list
    inner class BoardNoticeListViewHolder(private val binding: ItemNoticeListBinding) : RecyclerView.ViewHolder(binding.root) {
        var indexTv = binding.itemNoticeListIndex
        fun bind(notice : Notice, position: Int) {
            binding.notice = notice
            indexTv.text = (position + 1).toString()
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardNoticeListViewHolder {
        return BoardNoticeListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_notice_list,parent,false))
    }

    override fun onBindViewHolder(holder: BoardNoticeListViewHolder, position: Int) {
        holder.apply {
            bind(filteredList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if(charString.isEmpty()){
                    list
                }else{
                    val filteringList = ArrayList<Notice>()
                    for(item in list!!){
                        if(item.typeId!=null){
                            if(item!!.typeId == Integer.parseInt(charString))
                                filteringList.add(item)
                        }

                    }

                    Log.d(TAG, "performFiltering: $filteringList")
                    filteringList
                }
                val filterResult = FilterResults()
                filterResult.values = filteredList
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Notice>
                notifyDataSetChanged()
            }

        }
    }
}