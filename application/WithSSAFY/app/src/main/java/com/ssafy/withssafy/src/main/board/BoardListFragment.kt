package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardListBinding
import com.ssafy.withssafy.src.main.MainActivity


class BoardListFragment : BaseFragment<FragmentBoardListBinding>(FragmentBoardListBinding::bind, R.layout.fragment_board_list) {
    private lateinit var mainActivity: MainActivity

    private lateinit var boardListAdapter: BoardListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFaqAdapter()
    }

    private fun initFaqAdapter() {

        boardListAdapter = BoardListAdapter()
        val list = mutableListOf<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        boardListAdapter.list = list

        binding.boardListFragmentRvBoardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = boardListAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        boardListAdapter.setItemClickListener(object : BoardListAdapter.ItemClickListener {

            override fun onClick(view: View, position: Int, boardId: Int) {
                this@BoardListFragment.findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment,
                    bundleOf("boardId" to boardId)  // 게시판 타입
                )
            }
        })
    }
}