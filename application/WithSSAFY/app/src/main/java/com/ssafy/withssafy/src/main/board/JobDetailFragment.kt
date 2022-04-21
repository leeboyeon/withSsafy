package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentJobDetailBinding

class JobDetailFragment : BaseFragment<FragmentJobDetailBinding>(FragmentJobDetailBinding::bind,R.layout.fragment_job_detail) {

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
        initButtons()
    }
    private fun initButtons(){

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