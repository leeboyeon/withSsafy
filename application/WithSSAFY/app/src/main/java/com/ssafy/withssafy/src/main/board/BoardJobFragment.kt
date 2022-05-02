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
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.FragmentBoardJobBinding
import com.ssafy.withssafy.src.dto.RecruitLike
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.team.TeamFragment
import com.ssafy.withssafy.src.network.service.RecruitService
import kotlinx.coroutines.runBlocking

private const val TAG = "BoardJobFragment"
class BoardJobFragment : BaseFragment<FragmentBoardJobBinding>(FragmentBoardJobBinding::bind,R.layout.fragment_board_job) {
    private lateinit var mainActivity: MainActivity
    private lateinit var jobAdapter: JobAdapter

    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
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
        binding.viewModel = recruitViewModel
        runBlocking {
            recruitViewModel.getRecruitList()
        }
        setListener()
    }
    private fun setListener(){
        initAdmin()
        initButtons()
        initAdapter()
    }
    private fun initAdmin(){
        if(studentId != null) { // 교육생
            binding.fragmentJobAdminWrite.visibility = View.GONE
        } else { // 관리자
            binding.fragmentJobAdminWrite.visibility = View.VISIBLE
        }
    }
    private fun initButtons(){
        binding.fragmentJobAppBarPrev.setOnClickListener {
            this@BoardJobFragment.findNavController().popBackStack()
        }
        binding.fragmentJobAdminWrite.setOnClickListener {
            this@BoardJobFragment.findNavController().navigate(R.id.action_boardJobFragment_to_adminJobWriteFragment)
        }
    }
    private fun initAdapter(){
        jobAdapter = JobAdapter()
        jobAdapter.list = recruitViewModel.recruitList.value!!

        binding.fragmentJobRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = jobAdapter
        }
        jobAdapter.setItemClickListener(object : JobAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, id: Int) {
                var recruitId = bundleOf("recruitId" to id)
                this@BoardJobFragment.findNavController().navigate(R.id.jobDetailFragment, recruitId)
            }

        })
        jobAdapter.setHeartClickListener(object : JobAdapter.HeartClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                jobLikeAndCancel(id)
            }

        })
    }

    private fun jobLikeAndCancel(id: Int) {
        var recruitLike = RecruitLike(id, userId)
        runBlocking {
            val response = RecruitService().likeAndCancelRecruit(recruitLike)
            Log.d(TAG, "likeAndCancelRecruit: ${response.body()}")
           
            if(response.code() == 204) {
                Log.d(TAG, "jobLikeAndCancel: 채용 공고 찜하기 & 찜하기 취소 성공")
      
            } else {
                Log.d(TAG, "jobLikeAndCancel: 채용 공고 찜하기 & 찜하기 취소 실패")
            }
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