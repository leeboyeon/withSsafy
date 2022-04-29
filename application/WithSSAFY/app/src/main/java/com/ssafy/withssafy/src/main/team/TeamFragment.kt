package com.ssafy.withssafy.src.main.team

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamBinding
import com.ssafy.withssafy.src.network.service.StudyService
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "TeamFragment"
class TeamFragment : BaseFragment<FragmentTeamBinding>(FragmentTeamBinding::bind,R.layout.fragment_team) {
    private lateinit var teamAdapter: TeamAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = teamViewModel
        runBlocking {
            teamViewModel.getStudys()
        }
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
        teamAdapter = TeamAdapter(requireContext())

        binding.frargmentTeamTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    teamAdapter.filter.filter("")
                }else{
                    teamAdapter.filter.filter(tab?.text.toString())
                }

                teamAdapter.notifyDataSetChanged()
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
        teamAdapter = TeamAdapter(requireContext())

        teamAdapter.list = teamViewModel.studyList.value!!
        teamAdapter.filter.filter("")

        teamViewModel.studyList.observe(viewLifecycleOwner){
            teamAdapter.list = it
        }

        binding.fragmentTeamRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = teamAdapter
        }
        teamAdapter.setItemClickListener(object : TeamAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var studyId = bundleOf("studyId" to id)
                this@TeamFragment.findNavController().navigate(R.id.teamDetailFragment, studyId)
            }
        })
        teamAdapter.setDeleteClickListener(object : TeamAdapter.DeleteClickListener {
            override fun onClick(position: Int, id: Int) {
                deleteStudy(id,position)
            }
        })
        teamAdapter.setModifyClickListener(object : TeamAdapter.ModifyClickListener {
            override fun onClick(position: Int, id: Int) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun deleteStudy(id:Int, position:Int){
        var response : Response<Any?>
        runBlocking {
            response = StudyService().deleteStudy(id)
        }
        Log.d(TAG, "deleteStudy: ${response.code()}")
        if(response.isSuccessful){
            Log.d(TAG, "deleteStudy: 1")
            showCustomToast("삭제되었습니다.")
            Log.d(TAG, "deleteStudy: 2")
            runBlocking {
                teamViewModel.getStudys()
            }
            teamAdapter.notifyDataSetChanged()
//            teamAdapter.notifyItemRemoved(position)
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