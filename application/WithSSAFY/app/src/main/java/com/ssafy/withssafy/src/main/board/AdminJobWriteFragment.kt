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
import com.google.android.material.datepicker.MaterialDatePicker
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentAdminJobWriteBinding
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.network.service.RecruitService
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "AdminJobWriteFragment"
class AdminJobWriteFragment : BaseFragment<FragmentAdminJobWriteBinding>(FragmentAdminJobWriteBinding::bind,R.layout.fragment_admin_job_write) {
    private var edu = ""
    private var prefer = ""
    private var employType = ""
    private var career = ""
    private var startDate = ""
    private var endDate = ""
    private var recruitId = 0

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recruitId = it.getInt("recruitId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(recruitId != 0) {
            runBlocking {
                recruitViewModel.getRecruit(recruitId)
            }
        }
        setListener()
        initSpinner()
        selectSpinner()
        selectCheckBox()
        initData()
    }

    private fun setListener(){
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentJobWriteAppBarPrev.setOnClickListener {
            this@AdminJobWriteFragment.findNavController().popBackStack()
        }

        binding.fragmentJobWriteDatePickerBtn.setOnClickListener {
            showDataRangePicker()
        }

        binding.fragmentJobWriteWrite.setOnClickListener {
            runBlocking {
                insertRecruit()
            }
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

    /**
     * 수정하려고 넘어왔을 때 기존 데이터 초기화
     */
    private fun initData() {
        val recruitData = recruitViewModel.recruit.value
        binding.fragmentJobWriteCompanyInfoNameEdit.setText(recruitData!!.company)
        binding.fragmentJobWriteCompanyInfoAddrEdit.setText(recruitData!!.location)
        binding.fragmentJobWriteCompanyInfoSalaryEdit.setText(recruitData!!.salary)
        binding.fragmentJobWriteCompanyInfoWorkTimeEdit.setText(recruitData!!.workingHours)
        binding.fragmentJobWriteJobEdit.setText(recruitData!!.job)
        binding.fragmentJobWriteTaskEdit.setText(recruitData!!.taskDescription)
        binding.fragmentJobWriteWelfareEdit.setText(recruitData!!.welfare)
        binding.fragmentJobWriteDatePickerBtn.setText("${recruitData.startDate}부터 ${recruitData.endDate}까지")
        val c = recruitData.career // 경력
        val e = recruitData.education // 학력
        val p = recruitData.preferenceDescription // 우대사항
        val t = recruitData.employType // 고용형태
        if(c == "신입") {
            binding.fragmentJobWriteAddInfoCareerNew.isChecked = true
        } else if(c == "경력") {
            binding.fragmentJobWriteAddInfoCareerSenior.isChecked = true
        }
        if(e == "학사이상") {
            binding.fragmentJobWriteEduSpinner.setSelection(1)
        } else if(e == "관련학과 기졸업자") {
            binding.fragmentJobWriteEduSpinner.setSelection(2)
        } else if(e == "학사이상(예정자 포함)") {
            binding.fragmentJobWriteEduSpinner.setSelection(3)
        }
        if(p == "SSAFY 전형") {
            binding.fragmentJobWritePreferenceSpinner.setSelection(1)
        } else if(p == "정보처리기사 자격증 소지자") {
            binding.fragmentJobWritePreferenceSpinner.setSelection(2)
        }
        if(t == "정규직") {
            binding.fragmentJobWriteCareerTypeSpinner.setSelection(1)
        } else if(t == "계약직") {
            binding.fragmentJobWriteCareerTypeSpinner.setSelection(2)
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

    private fun selectCheckBox() {
        binding.fragmentJobWriteAddInfoCareerSenior.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked) career = "경력"
            else  career = ""
            Log.d(TAG, "selectCheckBox: $career")
        }

        binding.fragmentJobWriteAddInfoCareerNew.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked) career = "신입"
            else  career = ""
            Log.d(TAG, "selectCheckBox: $career")
        }
    }

    fun showDataRangePicker(){
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()
        dateRangePicker.show(childFragmentManager, "date_picker")
        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection?.first ?: 0
            val sDate = selection.first
            startDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
            Log.d("start", startDate)

            calendar.timeInMillis = selection?.second ?: 0
            val eDate = selection.second
            endDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
            Log.d("end", endDate)

            binding.fragmentJobWriteDatePickerBtn.text = "${startDate} ~ ${endDate}"
        }
    }

    private suspend fun insertRecruit() {
        var career = career
        var company = binding.fragmentJobWriteCompanyInfoNameEdit.text.toString()
        var education = edu
        var employType = employType
        var endDate = endDate
        var job = binding.fragmentJobWriteJobEdit.text.toString()
        var location = binding.fragmentJobWriteCompanyInfoAddrEdit.text.toString()
        var preferenceDescription = prefer
        var salary = binding.fragmentJobWriteCompanyInfoSalaryEdit.text.toString()
        var startDate = startDate
        var taskDescription = binding.fragmentJobWriteTaskEdit.text.toString()
        var welfare = binding.fragmentJobWriteWelfareEdit.text.toString()
        var workingHours = binding.fragmentJobWriteCompanyInfoWorkTimeEdit.text.toString()

        var recruit = Recruit(career, company, education, employType, endDate, job, location, preferenceDescription, salary, startDate, taskDescription, userId, welfare, workingHours)
        runBlocking {
            val response = RecruitService().insertRecruit(recruit)
            Log.d(TAG, "insertRecruit: ${response.body()}")
            Log.d(TAG, "insertRecruit: ${response.code()}")
            if(response.code() == 204) {
                showCustomToast("채용 공고 작성이 완료되었습니다.")
                this@AdminJobWriteFragment.findNavController().popBackStack()
            } else {
                showCustomToast("채용 공고 작성이 실패했습니다.")
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