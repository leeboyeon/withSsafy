package com.ssafy.withssafy.src.main.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamBinding


class TeamFragment : BaseFragment<FragmentTeamBinding>(FragmentTeamBinding::bind,R.layout.fragment_team) {
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
        binding.fragmentTeamWrite.setOnClickListener {
//            this@TeamFragment.findNavController().navigate(R.id.)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}