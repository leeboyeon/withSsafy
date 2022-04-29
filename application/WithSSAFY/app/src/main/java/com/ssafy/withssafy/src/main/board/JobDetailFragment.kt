package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentJobDetailBinding
import kotlinx.coroutines.runBlocking

class JobDetailFragment : BaseFragment<FragmentJobDetailBinding>(FragmentJobDetailBinding::bind,R.layout.fragment_job_detail) {
    private var recruitId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recruitId = it.getInt("recruitId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = recruitViewModel
        runBlocking {
            recruitViewModel.getRecruit(recruitId)
        }
        setListener()
    }
    private fun setListener(){
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentJobDetailAppBarPrev.setOnClickListener{
            this@JobDetailFragment.findNavController().popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}