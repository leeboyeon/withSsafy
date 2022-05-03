package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentNoticeWriteBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking


class NoticeWriteFragment : BaseFragment<FragmentNoticeWriteBinding>(FragmentNoticeWriteBinding::bind, R.layout.fragment_notice_write) {
    private var type = 0
    private var gen = ""
    private var area = ""
    private lateinit var mainActivity:MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getClassRoomListInit()
        initSpinner()
        selectSpinner()
    }

    private fun getClassRoomListInit() {
        runBlocking {
            userViewModel.getClassRoomList()
        }
    }

    private fun initSpinner() {
        var typeList = arrayListOf("선택", "학습", "평가", "운영", "사이트", "기타")
        var genList = arrayListOf("선택", "전체")
        var areaList = arrayListOf("선택", "전체", "서울", "대전", "광주", "구미", "부울경")

        val classRoomList = userViewModel.classRommList.value
        for(i in classRoomList!!) {
            genList.add(i.generation)
        }
        val newGenList = genList.toSet()

        val typeSpin = binding.fragmentNoticeWriteTypeSpin
        val genSpin = binding.fragmentNoticeWriteGenSpin
        val areaSpin = binding.fragmentNoticeWriteAreaSpin

        typeSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, typeList)
        }
        genSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newGenList.toList())
        }
        areaSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, areaList)
        }
    }

    private fun selectSpinner() {
        val typeSpin = binding.fragmentNoticeWriteTypeSpin
        val genSpin = binding.fragmentNoticeWriteGenSpin
        val areaSpin = binding.fragmentNoticeWriteAreaSpin

        typeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        genSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        gen = ""
                    }
                    else -> {
                        gen = genSpin.selectedItem.toString()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        areaSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        area = ""
                    }
                    else -> {
                        area = areaSpin.selectedItem.toString()
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


}