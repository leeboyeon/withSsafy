package com.ssafy.withssafy.src.main.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.DialogReportDetailBinding
import com.ssafy.withssafy.databinding.FragmentReportBinding
import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.dto.report.ReportRequest
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.board.CommentAdapter
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import com.ssafy.withssafy.src.network.service.ReportService
import com.ssafy.withssafy.util.CommonUtils
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response

class ReportFragment : BaseFragment<FragmentReportBinding>(FragmentReportBinding::bind, R.layout.fragment_report) {
    private lateinit var mainActivity: MainActivity

    private lateinit var reportDetailAdapter: ReportDetailAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        runBlocking {
            homeViewModel.getReportList()
        }
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding.reportFragmentAppBarPrev.setOnClickListener {
            this@ReportFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        reportDetailAdapter = ReportDetailAdapter()
        homeViewModel.reportList.observe(viewLifecycleOwner) {
            reportDetailAdapter.list = it
        }
        binding.reportFragmentRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reportDetailAdapter
        }
        binding.reportFragmentRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        reportDetailAdapter.setItemClickListener(object : ReportDetailAdapter.ItemClickListener {
            override fun onClick(view: View, report: Report) {
                showReportHandlingDialog(report)
            }

        })
    }


    private fun showReportHandlingDialog(report: Report) {
        val dialogView = LayoutInflater.from(TedPermissionProvider.context).inflate(R.layout.dialog_report_detail,null)

        if(dialogView.parent != null){
            (dialogView.parent as ViewGroup).removeAllViews()
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val params = dialog.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = params

        var boardOrComment = true // true : 게시글, false : 댓글

        dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvReportUserName).text = report.user.name // 신고자 이름
        dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvReportDate).text = CommonUtils.unixTimeToDateFormatInBoard(report.write_dt.toLong()) // 신고 시간
        dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvReportContent).text = report.content

        val titleLayout = dialogView.findViewById<ConstraintLayout>(R.id.reportDetailDialog_clBoardTitle)
        val title = dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvBoardTitle)
        val content = dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvBoardContent)

        if(report.comment != null) {    // comment 신고인 경우
            titleLayout.visibility = View.GONE
            content.text = report.comment.content
            boardOrComment = false

        } else if(report.board != null) {
            titleLayout.visibility = View.VISIBLE
            title.text = report.board.title
            content.text = report.board.content
            boardOrComment = true
        }

        dialog.show()

        dialogView.findViewById<Button>(R.id.reportDetailDialog_btnCancel).setOnClickListener { // 신고 반려 -> report delete
            dialog.dismiss()
        }

        dialogView.findViewById<AppCompatButton>(R.id.reportDetailDialog_btnReportConfirm).setOnClickListener { // 신고 접수 -> 해당 게시글 및 댓글 삭제

//            if(boardOrComment == true) {
//
//            }
//
//            try {
//                var response : Response<List<Report>>
//                runBlocking {
//                    response = ReportService().addReport(report)
//                }
//                if(response.isSuccessful) {
//                    val res = response.body()
//                    if(res != null) {
//                        if(res.size < 3) {    // 신고 횟수가 3회 미만
//                            showCustomToast("신고가 접수되었습니다.\n관리자 확인 후 처리될 예정입니다.\n")
//                            Log.d(com.ssafy.withssafy.src.main.TAG, "report: $response", )
//                        } else {
//                            val firstReport = res[0]
//                            if(firstReport.comment != null) { // 댓글 신고 횟수 4회 이상 - 해당 댓글 삭제
//                                var deleteCmtResponse : Response<Any?>
//
//                                runBlocking {
//                                    deleteCmtResponse = CommentService().deleteComment(firstReport.comment.id)
//                                }
//
//                                if(deleteCmtResponse.isSuccessful) {
//                                    showCustomToast("누적된 신고 횟수가 기준치를 초과하였기에 해당 댓글은 삭제 처리 되었습니다.\n")
//                                    runBlocking {
//                                        boardViewModel.getCommentList(firstReport.comment.boardId)
//                                    }
//                                    val commentAdapter = CommentAdapter(this)
//                                    commentAdapter.notifyDataSetChanged()
//                                }
//                            } else if(firstReport.board != null) {    // 게시글 신고 횟수 4회 이상 - 해당 게시글 삭제
//                                var deletePostResponse : Response<Any?>
//
//                                runBlocking {
//                                    deletePostResponse = BoardService().deletePost(firstReport.board.id)
//                                }
//
//                                if(deletePostResponse.isSuccessful) {
//                                    showCustomToast("누적된 신고 횟수가 기준치를 초과하였기에 해당 게시글은 삭제 처리 되었습니다.\n")
//                                    runBlocking {
//                                        boardViewModel.getPostDetail(firstReport.board.id)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    dialog.dismiss()
//                } else {
//                    Log.e(com.ssafy.withssafy.src.main.TAG, "report: 통신 실패", )
//                }
//            } catch (e: HttpException) {
//                Log.e(com.ssafy.withssafy.src.main.TAG, "report ${e.message()}", )
//            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}