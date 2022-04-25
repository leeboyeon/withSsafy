package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.FragmentBoardJobBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.team.TeamFragment


class BoardJobFragment : BaseFragment<FragmentBoardJobBinding>(FragmentBoardJobBinding::bind,R.layout.fragment_board_job) {
    private lateinit var mainActivity: MainActivity
    private lateinit var jobAdapter:JobAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initAdmin()
        initButtons()
        initAdapter()
    }
    private fun initAdmin(){
        binding.fragmentJobAdminWrite.visibility = View.VISIBLE
    }
    private fun initButtons(){
        binding.fragmentJobAppBarPrev.setOnClickListener {
            this@BoardJobFragment.findNavController().popBackStack()
        }
    }
    private fun initAdapter(){
        jobAdapter = JobAdapter()
        binding.fragmentJobRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = jobAdapter
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoardJobFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}