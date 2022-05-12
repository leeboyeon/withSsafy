package com.ssafy.withssafy.src.main.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentReportBinding
import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking

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
                showCustomToast(report.toString())
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}