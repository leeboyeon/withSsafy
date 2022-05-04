package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeDetailBinding
import kotlinx.coroutines.runBlocking


class BoardNoticeDetailFragment : BaseFragment<FragmentBoardNoticeDetailBinding>(FragmentBoardNoticeDetailBinding::bind, R.layout.fragment_board_notice_detail) {

    private var noticeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noticeId = it.getInt("noticeId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        runBlocking {
            noticeViewModel.getNotice(noticeId)
        }
        initButton()
    }

    private fun initButton() {
        binding.fragmentBoardNoticeDetailAppBarPrev.setOnClickListener{
            this@BoardNoticeDetailFragment.findNavController().popBackStack()
        }
    }

}