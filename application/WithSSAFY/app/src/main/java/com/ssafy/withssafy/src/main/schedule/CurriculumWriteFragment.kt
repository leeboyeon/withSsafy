package com.ssafy.withssafy.src.main.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCurriculumWriteBinding
import com.ssafy.withssafy.src.dto.Schedule
import java.util.*

class CurriculumWriteFragment : BaseFragment<FragmentCurriculumWriteBinding>(FragmentCurriculumWriteBinding::bind,R.layout.fragment_curriculum_write) {
    private var type = -1
    private var titleType = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initCheckBox()
        initSpinner()
        initDatePicker()
        initTimePicker()
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentCurrculWriteAddBucketBtn.setOnClickListener {
            insertBucket()
        }
    }
    private fun insertBucket(){
        var schedule = Schedule(
            classRoomId = ApplicationClass.sharedPreferencesUtil.getUser().classRoomId,
            endDate = "${binding.fragmentCurrculWriteEndDateTv.text.toString()} ${binding.fragmentCurrculWriteEndTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteEndTimeTv.length())}",
            0,
            memo = "",
            startDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteStartTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteStartTimeTv.length())}",
            title = titleType,
            ApplicationClass.sharedPreferencesUtil.getUser().id
        )
        scheduleViewModel.insertScheduleBucket(schedule)
    }
    private fun initTimePicker(){
        //종료시간 선택
        binding.fragmentCurrculWriteEndTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                if(hourOfDay > 12){
                    binding.fragmentCurrculWriteEndTimeTv.text = "PM ${hourOfDay}:${minute}"
                }else{
                    binding.fragmentCurrculWriteEndTimeTv.text = "AM ${hourOfDay}:${minute}"
                }
            }
            TimePickerDialog(requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
        //시작시간 선택
        binding.fragmentCurrculWriteStartTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                if(hourOfDay > 12){
                    binding.fragmentCurrculWriteStartTimeTv.text = "PM ${hourOfDay}:${minute}"
                }else{
                    binding.fragmentCurrculWriteStartTimeTv.text = "AM ${hourOfDay}:${minute}"
                }
            }
            TimePickerDialog(requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
    }
    private fun initDatePicker(){
        //종료날짜 선택
        binding.fragmentCurrculWriteEndDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.fragmentCurrculWriteEndDateTv.text = "$year-${month + 1}-$dayOfMonth"
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        //시작날짜 선택
        binding.fragmentCurrculWriteStartDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.fragmentCurrculWriteStartDateTv.text = "$year-${month + 1}-$dayOfMonth"
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun initCheckBox(){
        //type = 0 이면 Class type = 1이면 Full
        binding.fragmentCurrculWriteTypeClass.setOnCheckedChangeListener {buttonView, isChecked ->
            if(isChecked){
                binding.fragmentCurrculWriteTypeFull.isChecked = false
                type = 0
            }else{
                binding.fragmentCurrculWriteTypeFull.isChecked = true
                type = 1
            }
        }

        binding.fragmentCurrculWriteTypeFull.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.fragmentCurrculWriteTypeClass.isChecked = false
                type = 1
            }else{
                binding.fragmentCurrculWriteTypeClass.isChecked = true
                type = 0
            }
        }
    }
    private fun initSpinner(){
        var title = arrayListOf<String>("오전미팅","프로젝트진행","오후미팅","종료미팅","중식","라이브방송","직접입력")
        binding.fragmentCurrculWriteTitleSpinner.adapter = ArrayAdapter(requireContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,title)
        binding.fragmentCurrculWriteTitleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 6){
                    binding.fragmentCurrculWriteTitleEdit.visibility = View.VISIBLE
                    titleType = binding.fragmentCurrculWriteTitleEdit.text.toString()
                }else{
                    binding.fragmentCurrculWriteTitleEdit.visibility = View.GONE
                    titleType = binding.fragmentCurrculWriteTitleSpinner.getItemAtPosition(position).toString()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurriculumWriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}