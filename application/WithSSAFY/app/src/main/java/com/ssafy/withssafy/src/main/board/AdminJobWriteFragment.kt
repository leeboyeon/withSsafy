package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentAdminJobWriteBinding

private const val TAG = "AdminJobWriteFragment"
class AdminJobWriteFragment : BaseFragment<FragmentAdminJobWriteBinding>(FragmentAdminJobWriteBinding::bind,R.layout.fragment_admin_job_write) {
    private var edu = ""
    private var prefer = ""
    private var employType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        initSpinner()
        selectSpinner()
    }
    private fun setListener(){
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentJobWriteAppBarPrev.setOnClickListener {
            this@AdminJobWriteFragment.findNavController().popBackStack()
        }
    }

    /**
     * 학력, 우대사항, 고용형태 spinner 초기화
     */
    private fun initSpinner() {
        var eduList = arrayListOf("선택", "학사이상", "관련학과 기졸업자", "학사이상(예정자 포함)")
        var preferList = arrayListOf("선택", "SSAFY 전형", "정보처리기사 자격증 소지자")
        var employTypeList = arrayListOf("선택", "정규직", "계약직")

        val eduSpin = binding.fragmentJobWriteEduSpinner
        val preferSpin = binding.fragmentJobWritePreferenceSpinner
        val employTypeSpin = binding.fragmentJobWriteCareerTypeSpinner

        eduSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, eduList)
        }

        preferSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, preferList.toList())
        }
        employTypeSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, employTypeList.toList())
        }
    }

    private fun selectSpinner() {
        val eduSpin = binding.fragmentJobWriteEduSpinner
        val preferSpin = binding.fragmentJobWritePreferenceSpinner
        val employTypeSpin = binding.fragmentJobWriteCareerTypeSpinner

        eduSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        edu = ""
                    } else -> {
                        edu = eduSpin.selectedItem.toString()
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        preferSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        prefer = ""
                    } else -> {
                        prefer = preferSpin.selectedItem.toString()
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        employTypeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        employType = ""
                    } else -> {
                        employType = employTypeSpin.selectedItem.toString()
                        Log.d(TAG, "onItemSelected: $edu $prefer $employType")
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminJobWriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}