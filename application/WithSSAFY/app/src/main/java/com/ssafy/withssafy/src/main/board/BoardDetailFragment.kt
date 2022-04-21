package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardDetailBinding
import com.ssafy.withssafy.src.main.MainActivity

class BoardDetailFragment : BaseFragment<FragmentBoardDetailBinding>(FragmentBoardDetailBinding::bind, R.layout.fragment_board_detail) {
    private val TAG = "BoardDetailFragment_ws"
    private lateinit var mainActivity: MainActivity
    private lateinit var boardDetailAdapter: BoardDetailAdapter

    private var boardId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initListener() {

        // 뒤로가기 버튼 클릭 이벤트
        binding.boardDetailFragmentIbBack.setOnClickListener {
            this@BoardDetailFragment.findNavController().popBackStack()
        }

//        // 글 작성 버튼 클릭 이벤트
//        binding.boardDetailFragmentBtnWritePost.setOnClickListener {
//            this@BoardDetailFragment.findNavController().navigate(R.id.action_localBoardFragment_to_writeLocalBoardFragment,
//                bundleOf("postId" to -1)
//            )
//        }

    }

    /**
     * 게시글 recyclerView 초기화 + rv 아이템 클릭 이벤트
     */
    private fun initRecyclerView() {

    }
}