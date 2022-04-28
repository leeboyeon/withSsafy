package com.ssafy.withssafy.src.main.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentMessageDetailBinding
import kotlinx.coroutines.runBlocking

class MessageDetailFragment : BaseFragment<FragmentMessageDetailBinding>(FragmentMessageDetailBinding::bind,R.layout.fragment_message_detail) {
    private var fromId = 0
    private lateinit var detailAdapter: MessageDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromId = it.getInt("groupId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = messageViewModel
        runBlocking {
            messageViewModel.getMessageTalk(ApplicationClass.sharedPreferencesUtil.getUser().id, fromId)
        }
        setListener()
    }
    private fun setListener(){
        initAdapter()
    }
    private fun initAdapter(){
        detailAdapter = MessageDetailAdapter()
        messageViewModel.messageTalk.observe(viewLifecycleOwner){
            detailAdapter.list = it
        }
        binding.fragmentMessgaeDetailRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = detailAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}