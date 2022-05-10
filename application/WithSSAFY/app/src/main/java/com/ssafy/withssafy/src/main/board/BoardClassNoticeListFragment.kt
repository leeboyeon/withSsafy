package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardClassNoticeListBinding
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking

private const val TAG = "BoardClassNoticeListFragment"
class BoardClassNoticeListFragment : BaseFragment<FragmentBoardClassNoticeListBinding>(FragmentBoardClassNoticeListBinding::bind, R.layout.fragment_board_class_notice_list) {
    private lateinit var mainActivity: MainActivity
    private lateinit var boardClassNoticeListAdapter: BoardClassNoticeListAdapter
    private var position = 0
    val classRoomId = ApplicationClass.sharedPreferencesUtil.getUser().classRoomId

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        runBlocking {
            noticeViewModel.getClassNoticeList(classRoomId)
        }
        initAdapter()
        initButton()

    }


    private fun initButton() {
        binding.fragmentBoardClassNoticeListAppBarPrev.setOnClickListener {
            this@BoardClassNoticeListFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        boardClassNoticeListAdapter = BoardClassNoticeListAdapter(userViewModel, viewLifecycleOwner)
        noticeViewModel.classNoticeList.observe(viewLifecycleOwner) {
            it.reverse()
            boardClassNoticeListAdapter.list = it
            binding.fragmentBoardClassNoticeListRv.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                adapter = boardClassNoticeListAdapter
            }
            Log.d(TAG, "initAdapter position: $position")
            if(position == 0) {
                binding.fragmentBoardClassNoticeListRv.scrollToPosition(it.size - 1)
            } else {
                binding.fragmentBoardClassNoticeListRv.smoothScrollToPosition(position)
            }
        }
    }





}