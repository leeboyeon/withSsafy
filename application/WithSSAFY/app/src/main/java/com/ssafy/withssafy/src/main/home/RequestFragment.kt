package com.ssafy.withssafy.src.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentRequestBinding
import kotlinx.coroutines.runBlocking


class RequestFragment : BaseFragment<FragmentRequestBinding>(FragmentRequestBinding::bind, R.layout.fragment_request) {
    lateinit var requestAdapter: RequestAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userViewModel = userViewModel
        runBlocking {
            userViewModel.getStateZeroUserList()
        }
        initButton()
        initAdapter()
    }

    private fun initButton() {
        binding.fragmentRequestAppBarPrev.setOnClickListener{
            this@RequestFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        requestAdapter = RequestAdapter(false)
        userViewModel.stateZeroUserList.observe(viewLifecycleOwner) {
            requestAdapter.list = it
        }
        binding.fragmentRequestRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = requestAdapter
        }
        binding.fragmentRequestRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }


}