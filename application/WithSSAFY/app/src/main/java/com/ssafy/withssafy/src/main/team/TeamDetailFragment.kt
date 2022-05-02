package com.ssafy.withssafy.src.main.team

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamDetailBinding
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.dto.study.StudyMember
import com.ssafy.withssafy.src.dto.study.StudyMemberRequest
import com.ssafy.withssafy.src.main.board.CommentAdapter
import com.ssafy.withssafy.src.network.service.MessageService
import com.ssafy.withssafy.src.network.service.StudyService
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import kotlinx.coroutines.runBlocking

private const val TAG = "TeamDetailFragment"
class TeamDetailFragment : BaseFragment<FragmentTeamDetailBinding>(FragmentTeamDetailBinding::bind, R.layout.fragment_team_detail) {
    private var studyId = 0
    private lateinit var studyCommentAdapter: TeamCommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studyId = it.getInt("studyId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = teamViewModel
        runBlocking {
            teamViewModel.getStudy(studyId)
            teamViewModel.getStudyCommentByBoardId(studyId)
        }
        setListener()
    }
    private fun setListener(){
        initButtons()
        initAdapter()
    }
    private fun initButtons(){
        binding.fragmentTeamDetailAppBarPrev.setOnClickListener {
            this@TeamDetailFragment.findNavController().popBackStack()
        }
        binding.fragmentTeamDetailRequest.setOnClickListener {
            showRequestDialog()
        }
    }
    private fun initAdapter(){
        studyCommentAdapter = TeamCommentAdapter(requireContext())
        teamViewModel.studyComments.observe(viewLifecycleOwner){
            studyCommentAdapter.commentAllList = it
        }
        teamViewModel.studyParentComments.observe(viewLifecycleOwner){
            studyCommentAdapter.commentList = it
        }
        studyCommentAdapter.postUserId = teamViewModel.study.value!!.user!!.id
        studyCommentAdapter.setAddReplyItemClickListener(object: TeamCommentAdapter.ItemClickListener {
            override fun onClick(view: View, writerNick: String, position: Int, commentId: Int) {
                
            }

        })
    }
    private fun showRequestDialog(){
        var dialog = Dialog(requireContext())
        var dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_team_request,null)
        if(dialogView.parent!=null){
            (dialogView.parent as ViewGroup).removeView(dialogView)
        }
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.findViewById<TextView>(R.id.fragment_team_requestStudyName).text = teamViewModel.study.value!!.title
        dialog.show()

        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestRequst).setOnClickListener {
            if(teamViewModel.study.value!!.studyMembers?.size != null){
                for(item in teamViewModel.study.value!!.studyMembers!!){
                    if(item.id == ApplicationClass.sharedPreferencesUtil.getUser().id){
                        dialog.dismiss()
                        Log.d(TAG, "showRequestDialog: 이미 추가된 사용자임")
                    }
                }
            }
            if(teamViewModel.study.value!!.user!!.id == ApplicationClass.sharedPreferencesUtil.getUser().id){
                dialog.dismiss()
                showCustomToast("본인의 스터디에는 신청하실 수 없습니다.")
            }else{
                var message = Message(
                    "[스터디 ${teamViewModel.study.value!!.id}] '${teamViewModel.study.value!!.title}’에 지원하였습니다.",
                    0,
                    ApplicationClass.sharedPreferencesUtil.getUser().id,
                    teamViewModel.study.value!!.user!!.id
                )
                runBlocking {
                    val response = MessageService().insertMessage(message)
                    Log.d(TAG, "showRequestDialog: ${response.code()}")
                    if(response.code() == 204){
                        Log.d(TAG, "showRequestDialog: success!")
                        dialog.dismiss()
                    }
                }
            }

        }
        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestCancle).setOnClickListener {
            dialog.dismiss()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}