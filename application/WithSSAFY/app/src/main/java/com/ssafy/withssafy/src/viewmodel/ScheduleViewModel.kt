package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.src.network.service.ScheduleService
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {

    private val scheduleBucketList = mutableListOf<Schedule>()
    var liveScheduleBucket = MutableLiveData<MutableList<Schedule>>().apply {
        value = scheduleBucketList
    }
    private val _classSchedules = MutableLiveData<MutableList<Schedule>>()

    val classSchedules : LiveData<MutableList<Schedule>>
        get() = _classSchedules

    fun setClassSchedules(list : MutableList<Schedule>){
        _classSchedules.value = list
    }
    fun insertScheduleBucket(schedule: Schedule){
        scheduleBucketList.add(schedule)
        liveScheduleBucket.value = scheduleBucketList
    }
    fun removeScheduleBucket(list:MutableList<Int>){
        for(i in 0..list.size-1){
            scheduleBucketList.removeAt(i)
        }
        liveScheduleBucket.value = scheduleBucketList
    }
    fun removeAllScheduleBucket(){
        scheduleBucketList.clear()
        liveScheduleBucket.value = scheduleBucketList
    }
    suspend fun getClassSchedule(id:Int){
        val response = ScheduleService().getSchedulesByMyClass(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code()== 200){
                if(res!=null){
                    setClassSchedules(res)
                }
            }
        }
    }
}