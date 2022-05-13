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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var studyCommentAdapter: CommentAdapter

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
        initData()
        initButtons()
        initAdapter()
        commentLayoutClickEvent()
    }
    private fun initData(){
        if(teamViewModel.study.value!!.photoPath == null || teamViewModel.study.value!!.photoPath == ""){
            binding.fragmentTeamDetailImg.visibility = View.GONE
        }
        if(teamViewModel.study.value!!.type == 1){
            binding.fragmentTeamDetailAppBarTitle.text = "팀빌딩모집"
        }else{
            binding.fragmentTeamDetailAppBarTitle.text = "스터디모집"
        }

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
        studyCommentAdapter = CommentAdapter(requireContext())
        teamViewModel.studyComments.observe(viewLifecycleOwner){
            studyCommentAdapter.commentAllList = it
        }
        teamViewModel.studyParentComments.observe(viewLifecycleOwner){
            studyCommentAdapter.commentList = it
        }
        studyCommentAdapter.postUserId = teamViewModel.study.value!!.user!!.id
        studyCommentAdapter.setAddReplyItemClickListener(object: CommentAdapter.ItemClickListener {
            override fun onClick(view: View, writerNick: String, position: Int, commentId: Int) {

            }
        })

        binding.postDetailFragmentRvComment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studyCommentAdapter
        }
    }
    private fun commentLayoutClickEvent(){
        binding.fragmentTeamDetailCommentInputLayout.setOnClickListener {
            var postId = bundleOf("studyId" to studyId)
            this@TeamDetailFragment.findNavController().navigate(R.id.studyCommentFragment,postId)
        }
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

        runBlocking {
            teamViewModel.getTeamInfo()
        }
        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestRequst).setOnClickListener {
            var team = teamViewModel.teamInfo.value!!
            runBlocking {
                userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id, 1)
            }
            if(team.classification == 0){
                if(teamViewModel.study.value!!.user!!.roomId != userViewModel.loginUserInfo.value!!.classRoomId)
                {
                    showCustomToast("같은 반의 팀빌딩만 신청하실 수 있습니다.")
                    dialog.dismiss()
                }
            }
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