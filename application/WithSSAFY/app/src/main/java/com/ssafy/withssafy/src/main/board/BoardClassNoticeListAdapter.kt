package com.ssafy.withssafy.src.main.board

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemBoardClassNoticeBinding
import com.ssafy.withssafy.databinding.ItemClassNoticeBinding
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.viewmodel.NoticeViewModel
import com.ssafy.withssafy.src.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking

class BoardClassNoticeListAdapter(val userViewModel: UserViewModel, val viewLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<BoardClassNoticeListAdapter.BoardClassNoticeListViewHolder>() {
    var list = mutableListOf<Notice>()
    inner class BoardClassNoticeListViewHolder(private val binding: ItemClassNoticeBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice : Notice) {
            binding.notice = notice
            runBlocking {
                userViewModel.getUserInfoAuth(notice.userId)
            }
            binding.user = userViewModel.userInfoAuth.value!!
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardClassNoticeListAdapter.BoardClassNoticeListViewHolder {
        return BoardClassNoticeListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_class_notice,parent,false))
    }

    override fun onBindViewHolder(holder: BoardClassNoticeListViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}