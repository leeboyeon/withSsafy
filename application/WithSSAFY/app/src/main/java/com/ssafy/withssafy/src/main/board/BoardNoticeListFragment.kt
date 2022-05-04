package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeListBinding
import com.ssafy.withssafy.src.main.team.TeamAdapter


class BoardNoticeListFragment : BaseFragment<FragmentBoardNoticeListBinding>(FragmentBoardNoticeListBinding::bind, R.layout.fragment_board_notice_list) {
    private lateinit var boardNoticeListAdapter: BoardNoticeListAdapter
    private var typeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeId = it.getInt("typeId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        initAdapter()
        initTabLayout()
    }

    private fun initButton() {
        binding.fragmentBoardNoticeListAppBarPrev.setOnClickListener {
            this@BoardNoticeListFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        boardNoticeListAdapter = BoardNoticeListAdapter()
        boardNoticeListAdapter.list = noticeViewModel.noticeAllList.value!!
        boardNoticeListAdapter.filter.filter("")
        boardNoticeListAdapter.filteredList = boardNoticeListAdapter.list

//        noticeViewModel.noticeAllList.observe(viewLifecycleOwner) {
//            boardNoticeListAdapter.list = it
//            boardNoticeListAdapter.filteredList = boardNoticeListAdapter.list
//        }
        binding.fragmentBoardNoticeListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = boardNoticeListAdapter
        }
    }

    private fun initTabLayout() {
        val types = arrayListOf("학습","평가","운영","사이트","기타")
        for(item in types){
            binding.frargmentBoardNoticeListTabLayout.addTab(binding.frargmentBoardNoticeListTabLayout.newTab().setText(item))
        }
        boardNoticeListAdapter = BoardNoticeListAdapter()
        binding.frargmentBoardNoticeListTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    boardNoticeListAdapter.filter.filter("")
                }else{
                    boardNoticeListAdapter.filter.filter(tab?.position.toString())
                }
                boardNoticeListAdapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }



}