package com.ssafy.withssafy.src.main.schedule

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.core.view.get
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentClassCurriculumBinding
import com.ssafy.withssafy.src.dto.Schedule
import kotlinx.coroutines.runBlocking

class ClassCurriculumFragment : BaseFragment<FragmentClassCurriculumBinding>(FragmentClassCurriculumBinding::bind,R.layout.fragment_class_curriculum) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id,1)
            scheduleViewModel.getClassSchedule(userViewModel.loginUserInfo.value!!.classRoomId,18)
        }
        setListener()
    }
    private fun setListener(){
        addSchedule()
    }
    private fun addSchedule(){
        var schedules = mutableListOf<Schedule>()
        scheduleViewModel.classSchedules.observe(viewLifecycleOwner){
            schedules = it
        }
        for(item in schedules){
            var startTimes = item.startDate.substring(item.startDate.length-5,item.startDate.length)
            var endTimes = item.endDate.substring(item.endDate.length-5,item.endDate.length)
            var startHour = startTimes.substring(0,2).toInt()
            var endHour = endTimes.substring(0,2).toInt()
            var diff = endHour - startHour
            if(startHour == 9){

                binding.monday0.setBackgroundColor(Color.WHITE)
                binding.monday0.setText(item.title)
            }
            if(startHour == 10){
                binding.monday1.setBackgroundColor(Color.WHITE)
                binding.monday1.setText(item.title)
            }
            if(startHour == 11){
                binding.monday2.setBackgroundColor(Color.WHITE)
                binding.monday2.setText(item.title)
            }
            if(startHour == 12){
                binding.monday3.setBackgroundColor(Color.WHITE)
                binding.monday3.setText(item.title)
            }
            if(startHour == 13){
                binding.monday4.setBackgroundColor(Color.WHITE)
                binding.monday4.setText(item.title)
            }
            if(startHour == 14){
                binding.monday5.setBackgroundColor(Color.WHITE)
                binding.monday5.setText(item.title)
            }
            if(startHour == 15){
                binding.monday6.setBackgroundColor(Color.WHITE)
                binding.monday6.setText(item.title)
            }
            if(startHour == 16){
                binding.monday7.setBackgroundColor(Color.WHITE)
                binding.monday7.setText(item.title)
            }
            if(startHour == 17){
                binding.monday8.setBackgroundColor(Color.WHITE)
                binding.monday8.setText(item.title)
            }
            if(startHour == 18){
                binding.monday9.setBackgroundColor(Color.WHITE)
                binding.monday9.setText(item.title)
            }

            if(item.weeks == 1){
                if(startHour == 9){
                    binding.monday0.setBackgroundColor(Color.WHITE)
                    binding.monday0.setText(item.title)
                }
                if(startHour == 10){
                    binding.monday1.setBackgroundColor(Color.WHITE)
                    binding.monday1.setText(item.title)
                }
                if(startHour == 11){
                    binding.monday2.setBackgroundColor(Color.WHITE)
                    binding.monday2.setText(item.title)
                }
                if(startHour == 12){
                    binding.monday3.setBackgroundColor(Color.WHITE)
                    binding.monday3.setText(item.title)
                }
                if(startHour == 13){
                    binding.monday4.setBackgroundColor(Color.WHITE)
                    binding.monday4.setText(item.title)
                }
                if(startHour == 14){
                    binding.monday5.setBackgroundColor(Color.WHITE)
                    binding.monday5.setText(item.title)
                }
                if(startHour == 15){
                    binding.monday6.setBackgroundColor(Color.WHITE)
                    binding.monday6.setText(item.title)
                }
                if(startHour == 16){
                    binding.monday7.setBackgroundColor(Color.WHITE)
                    binding.monday7.setText(item.title)
                }
                if(startHour == 17){
                    binding.monday8.setBackgroundColor(Color.WHITE)
                    binding.monday8.setText(item.title)
                }
                if(startHour == 18){
                    binding.monday9.setBackgroundColor(Color.WHITE)
                    binding.monday9.setText(item.title)
                }
            }
        }
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