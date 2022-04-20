package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeBinding
import com.ssafy.withssafy.src.main.MainActivity


class BoardNoticeFragment : BaseFragment<FragmentBoardNoticeBinding>(FragmentBoardNoticeBinding::bind, R.layout.fragment_board_notice) {
    private val TAG = "BoardNoticeFragment_ws"
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSsafyRuleDetailToggle()
    }

    private fun initSsafyRuleDetailToggle() {
        val attendanceDetail = binding.boardNotiFragmentIvAttendanceDetail
        val attendance = binding.boardNotiFragmentIvAttendance

        attendanceDetail.setOnClickListener {
            if(attendance.visibility == View.GONE) { // content가 숨겨져 있는 경우
                attendanceDetail.setImageResource(R.drawable.ic_arrow_up)
                attendance.visibility = View.VISIBLE
            } else if(attendance.visibility == View.VISIBLE) {
                attendanceDetail.setImageResource(R.drawable.ic_arrow_down)
                attendance.visibility = View.GONE
            }
        }


        val lifeRuleDetail = binding.boardNotiFragmentIvLifeRuleDetail
        val lifeRule = binding.boardNotiFragmentIvLifeRule

        lifeRuleDetail.setOnClickListener {
            if(lifeRule.visibility == View.GONE) { // content가 숨겨져 있는 경우
                lifeRuleDetail.setImageResource(R.drawable.ic_arrow_up)
                lifeRule.visibility = View.VISIBLE
            } else if(lifeRule.visibility == View.VISIBLE) {
                lifeRuleDetail.setImageResource(R.drawable.ic_arrow_down)
                lifeRule.visibility = View.GONE
            }
        }


        val mileageDetail = binding.boardNotiFragmentIvMileageDetail
        val mileage = binding.boardNotiFragmentIvMileage

        mileageDetail.setOnClickListener {
            if(mileage.visibility == View.GONE) { // content가 숨겨져 있는 경우
                mileageDetail.setImageResource(R.drawable.ic_arrow_up)
                mileage.visibility = View.VISIBLE
            } else if(mileage.visibility == View.VISIBLE) {
                mileageDetail.setImageResource(R.drawable.ic_arrow_down)
                mileage.visibility = View.GONE
            }
        }

    }

}