package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeDetailBinding
import kotlinx.coroutines.runBlocking

private const val TAG = "BoardNoticeDetailFragment"
class BoardNoticeDetailFragment : BaseFragment<FragmentBoardNoticeDetailBinding>(FragmentBoardNoticeDetailBinding::bind, R.layout.fragment_board_notice_detail) {

    private var noticeId = 0
    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noticeId = it.getInt("noticeId")
        }
        Log.d(TAG, "getNoticeOne: $noticeId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        runBlocking {
            noticeViewModel.getNotice(noticeId)
        }
        initData()
        initButton()
    }

    private fun initButton() {
        if(studentId != null) { // 교육생
            binding.fragmentBoardNoticeDetailMore.visibility = View.GONE
        } else { // 관리자
            binding.fragmentBoardNoticeDetailMore.visibility = View.VISIBLE
            binding.fragmentBoardNoticeDetailMore.setOnClickListener {
                showPopMenu(it, noticeId)
            }
        }
        binding.fragmentBoardNoticeDetailAppBarPrev.setOnClickListener{
            this@BoardNoticeDetailFragment.findNavController().popBackStack()
        }
    }

    private fun initData(){
        if(noticeViewModel.notice.value!!.photoPath == null || noticeViewModel.notice.value!!.photoPath == ""){
            binding.fragmentBoardNoticeDetailImg.visibility = View.GONE
        }
    }

    private fun showPopMenu(view: View, id: Int) {
        val popup = PopupMenu(context, view)
        MenuInflater(context).inflate(R.menu.recruit_popup_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.update -> {
                    var noticeId = bundleOf("noticeId" to noticeId)
                    this@BoardNoticeDetailFragment.findNavController().navigate(R.id.noticeWriteFragment, noticeId)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete -> {
                    //deleteRecruit(id)
                    return@setOnMenuItemClickListener true
                } else -> {
                return@setOnMenuItemClickListener false
            }
            }
        }
    }

}