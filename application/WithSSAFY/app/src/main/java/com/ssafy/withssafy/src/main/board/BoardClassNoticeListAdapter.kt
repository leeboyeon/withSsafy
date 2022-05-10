package com.ssafy.withssafy.src.main.board

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
        var nameTxt = binding.itemClassNoticeName
        var authTxt = binding.itemClassNoticeAuth
        fun bind(notice : Notice) {
            binding.notice = notice
            binding.executePendingBindings()
            runBlocking {
                userViewModel.getUserInfoAuth(notice.userId)
            }
            userViewModel.userInfoAuth.observe(viewLifecycleOwner) {
                nameTxt.setText(it.user.name)
                if(it.auth == 0) {
                    authTxt.setText("(관리자)")
                } else if(it.auth == 1) {
                    authTxt.setText("(컨설턴트)")
                } else if(it.auth == 2) {
                    authTxt.setText("(프로)")
                }
            }

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