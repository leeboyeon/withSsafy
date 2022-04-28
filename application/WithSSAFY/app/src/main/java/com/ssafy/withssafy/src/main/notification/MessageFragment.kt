package com.ssafy.withssafy.src.main.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentMessageBinding


class MessageFragment : BaseFragment<FragmentMessageBinding>(FragmentMessageBinding::bind, R.layout.fragment_message) {
    private lateinit var groupAdapter: MessageGroupAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initAdapter()
    }
    private fun initAdapter(){
        groupAdapter = MessageGroupAdapter()
        binding.fragmentMessageGroupRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        groupAdapter.setItemClickListener(object : MessageGroupAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, groupId: Int) {
                var groupId = bundleOf("groupId" to groupId)
                this@MessageFragment.findNavController().navigate(R.id.messageDetailFragment, groupId)
            }

        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}