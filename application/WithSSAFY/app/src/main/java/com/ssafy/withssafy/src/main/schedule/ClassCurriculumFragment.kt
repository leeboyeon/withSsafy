package com.ssafy.withssafy.src.main.schedule

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ColorStateListDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.core.view.get
import com.github.tlaabs.timetableview.Sticker
import com.github.tlaabs.timetableview.Time
import com.github.tlaabs.timetableview.TimetableView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentClassCurriculumBinding
import com.ssafy.withssafy.src.dto.Schedule
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "ClassCurriculumFragment"
class ClassCurriculumFragment : BaseFragment<FragmentClassCurriculumBinding>(FragmentClassCurriculumBinding::bind,R.layout.fragment_class_curriculum) {
    private lateinit var timetable:TimetableView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id, 1)
            scheduleViewModel.getClassSchedule(userViewModel.loginUserInfo.value!!.classRoomId)
        }
        setListener()
    }

    private fun setListener() {
        initData()
        initTimeTable()
    }
    private fun initData(){
        binding.fragmentScheduleAppBarTitle.text = "${scheduleViewModel.classSchedules.value!![0].weeks.toString()}주차"
    }
    @SuppressLint("ResourceAsColor")
    private fun initTimeTable(){
        timetable = binding.timetable

        scheduleViewModel.classSchedules.observe(viewLifecycleOwner){
            var schedules = arrayListOf<com.github.tlaabs.timetableview.Schedule>()
            var idx = 0
            for(item in it){
                var startTime = item.startDate.substring(item.startDate.length-8,item.startDate.length)
                var startTimeHour = startTime.substring(0,2)
                var startTimeMinute = startTime.substring(3,5)
                var endTime = item.endDate.substring(item.endDate.length-8,item.endDate.length)
                var endTimeHour = endTime.substring(0,2)
                var endTimeMinute = endTime.substring(3,5)
                var schedule = com.github.tlaabs.timetableview.Schedule()

                schedule.startTime = Time(startTimeHour.toInt(), startTimeMinute.toInt())
                schedule.endTime = Time(endTimeHour.toInt(), endTimeMinute.toInt())
                schedule.day = findWeeks(item.startDate.substring(0,10))-1
                schedule.classTitle = item.title
                schedule.classPlace= ""
                schedule.professorName = ""
                schedules.add(schedule)
            }
            timetable.add(schedules)
        }

        var json = timetable.createSaveData()
        timetable.load(json)
    }
    private fun findWeeks(date:String):Int{
        var dateTime = LocalDate.parse(date)
        return dateTime.dayOfWeek.value!!
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClassCurriculumFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}