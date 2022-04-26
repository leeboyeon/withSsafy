package com.ssafy.withssafy.src.main.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamBinding
import com.ssafy.withssafy.src.viewmodel.TeamViewModel


class TeamFragment : BaseFragment<FragmentTeamBinding>(FragmentTeamBinding::bind,R.layout.fragment_team) {
    private val temViewModel : TeamViewModel by activityViewModels()
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
        initTabLayout()
        initAdapter()
    }
    private fun initTabLayout(){
        val types = arrayListOf<String>("어학","프로그래밍","면접","취업","CS","자율","기타")
        for(item in types){
            binding.frargmentTeamTabLayout.addTab(binding.frargmentTeamTabLayout.newTab().setText(item))
        }

        binding.frargmentTeamTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }
    private fun initButtons(){
        binding.fragmentTeamWrite.setOnClickListener {
            this@TeamFragment.findNavController().navigate(R.id.teamWriteFragment)
        }
    }
    private fun initAdapter(){
        
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