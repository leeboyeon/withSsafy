package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardDetailBinding
import com.ssafy.withssafy.src.main.MainActivity

class BoardDetailFragment : BaseFragment<FragmentBoardDetailBinding>(FragmentBoardDetailBinding::bind, R.layout.fragment_board_detail) {
    private val TAG = "BoardDetailFragment_ws"
    private lateinit var mainActivity: MainActivity

    private var boardId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}