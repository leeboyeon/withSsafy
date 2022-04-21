package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
//        boardListAdapter.list = list

        binding.boardListFragmentRvBoardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = boardListAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        boardListAdapter.setItemClickListener(object : BoardListAdapter.ItemClickListener {

            override fun onClick(view: View, contentView: TextView, position: Int) {
//                val arrow = view as ImageButton
//                if(contentView.visibility == View.GONE) { // content가 숨겨져 있는 경우
//                    arrow.setImageResource(R.drawable.ic_up_arrow)
//                    contentView.visibility = View.VISIBLE
//                } else if(contentView.visibility == View.VISIBLE) {
//                    arrow.setImageResource(R.drawable.ic_arrow_down)
//                    contentView.visibility = View.GONE
//                }

            }
        })
    }
}