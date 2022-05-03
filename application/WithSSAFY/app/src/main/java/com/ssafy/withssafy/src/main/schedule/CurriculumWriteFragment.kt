package com.ssafy.withssafy.src.main.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCurriculumWriteBinding
import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.src.network.service.ScheduleService
import kotlinx.coroutines.runBlocking
import java.util.*

private const val TAG = "CurriculumWriteFragment"
class CurriculumWriteFragment : BaseFragment<FragmentCurriculumWriteBinding>(FragmentCurriculumWriteBinding::bind,R.layout.fragment_curriculum_write) {
    private var type = -1
    private var titleType = "";
    private var week = 0
    private var roomId = 0
    private lateinit var writeAdapter:ClassCurrculWriteAdapter
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
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id,1)
        }

        roomId = userViewModel.loginUserInfo.value!!.classRoomId
        initCheckBox()
        initSpinner()
        initDatePicker()
        initTimePicker()
        initButtons()
        initAdapter()
    }
    private fun initAdapter(){
        writeAdapter = ClassCurrculWriteAdapter()
        scheduleViewModel.liveScheduleBucket.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")
            writeAdapter.list = it
        }
        binding.fragmentCurrculWriteBucketRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = writeAdapter
        }
    }
    private fun initButtons(){
        writeAdapter = ClassCurrculWriteAdapter()
        binding.fragmentCurrculWriteAddBucketBtn.setOnClickListener {
            insertBucket()
            writeAdapter.notifyDataSetChanged()
        }
        binding.fragmentCurrculWriteChecked.setOnClickListener {
            if(writeAdapter.checked == true){
                binding.fragmentCurrculWriteChecked.text = "선택"

                writeAdapter.updateCheckBox(false)
                writeAdapter.notifyDataSetChanged()
            }else{
                binding.fragmentCurrculWriteChecked.text = "선택취소"
                writeAdapter.updateCheckBox(true)
                writeAdapter.notifyDataSetChanged()
            }
        }
        binding.fragmentCurrculWriteBinBtn.setOnClickListener{
            scheduleViewModel.removeScheduleBucket(writeAdapter.checkList)
            writeAdapter.notifyDataSetChanged()
            writeAdapter.checkList = mutableListOf()
        }
        binding.fragmentCurrculWriteRemoveAll.setOnClickListener {
            scheduleViewModel.removeAllScheduleBucket()
            writeAdapter.notifyDataSetChanged()
        }
        binding.fragmentCurrculWriteInsert.setOnClickListener {
            insertSchedules()
        }
    }
    private fun insertSchedules(){
        var schedules = scheduleViewModel.liveScheduleBucket.value!!
        runBlocking {
            val response = ScheduleService().insertSchedule(schedules)
            if(response.code() == 204){
                this@CurriculumWriteFragment.findNavController().navigate(R.id.scheduleFragment)
            }
        }
    }
    private fun insertBucket(){

        Log.d(TAG, "insertBucket: $roomId")
        var schedule = Schedule(
            classRoomId = roomId,
            endDate = "${binding.fragmentCurrculWriteEndDateTv.text.toString()} ${binding.fragmentCurrculWriteEndTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteEndTimeTv.length())}",
            0,
            memo = "",
            startDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteStartTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteStartTimeTv.length())}",
            title = titleType,
            ApplicationClass.sharedPreferencesUtil.getUser().id,
            week
        )
        Log.d(TAG, "insertBucket: $schedule")
        scheduleViewModel.insertScheduleBucket(schedule)
    }
    private fun initTimePicker(){
        //종료시간 선택
        binding.fragmentCurrculWriteEndTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var hour = ""
            var minutes = ""
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                if(hourOfDay >= 10){
                    hour = hourOfDay.toString()
                }else{
                    hour = "0$hourOfDay"
                }
                if(minute >= 10){
                    minutes = minute.toString()
                }else{
                    minutes = "0$minute"
                }
                if(hour.toInt() > 12){
                    binding.fragmentCurrculWriteEndTimeTv.text = "PM ${hour}:${minutes}"
                }else{
                    binding.fragmentCurrculWriteEndTimeTv.text = "AM ${hour}:${minutes}"
                }
            }

            TimePickerDialog(requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
        //시작시간 선택
        binding.fragmentCurrculWriteStartTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var hour = ""
            var minutes = ""
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                if(hourOfDay >= 10){
                    hour = hourOfDay.toString()
                }else{
                    hour = "0$hourOfDay"
                }
                if(minute >= 10){
                    minutes = minute.toString()
                }else{
                    minutes = "0$minute"
                }
                if(hour.toInt() > 12){
                    binding.fragmentCurrculWriteStartTimeTv.text = "PM ${hour}:${minutes}"
                }else{
                    binding.fragmentCurrculWriteStartTimeTv.text = "AM ${hour}:${minutes}"
                }
            }
            TimePickerDialog(requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
    }
    private fun initDatePicker(){
        //종료날짜 선택
        binding.fragmentCurrculWriteEndDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var months = ""
            var days = ""
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if(month+1 >= 10){
                    months = (month+1).toString()
                }else{
                    months = "0${month+1}"
                }
                if(dayOfMonth >= 10){
                    days = dayOfMonth.toString()
                }else{
                    days = "0$dayOfMonth"
                }
                binding.fragmentCurrculWriteEndDateTv.text = "${year.toString()}-${months}-$days"
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        //시작날짜 선택
        binding.fragmentCurrculWriteStartDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var months = ""
            var days = ""
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if(month+1 >= 10){
                    months = (month+1).toString()
                }else{
                    months = "0${month+1}"
                }
                if(dayOfMonth >= 10){
                    days = dayOfMonth.toString()
                }else{
                    days = "0$dayOfMonth"
                }
                binding.fragmentCurrculWriteStartDateTv.text = "${year.toString()}-${months}-$days"
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

        var weeks = arrayListOf<String>()
        for(i in 1..30) {
            if(i >= 10){
                weeks.add("${i}주차")
            }else{
                weeks.add("0${i}주차")
            }

        }
        binding.fragmentCurrculWriteWeeksSpinner.adapter = ArrayAdapter(requireContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,weeks)
        binding.fragmentCurrculWriteWeeksSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                week = binding.fragmentCurrculWriteWeeksSpinner.getItemAtPosition(p2).toString().substring(0,2).toInt()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
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