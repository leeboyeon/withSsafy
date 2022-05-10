package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardClassNoticeListBinding
import com.ssafy.withssafy.src.main.MainActivity

private const val TAG = "BoardClassNoticeListFragment"
class BoardClassNoticeListFragment : BaseFragment<FragmentBoardClassNoticeListBinding>(FragmentBoardClassNoticeListBinding::bind, R.layout.fragment_board_class_notice_list) {
    private lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }





}