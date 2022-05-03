package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.withssafy.src.dto.Schedule

class ScheduleViewModel : ViewModel() {

    private val scheduleBucketList = mutableListOf<Schedule>()
    var liveScheduleBucket = MutableLiveData<MutableList<Schedule>>().apply {
        value = scheduleBucketList
    }
    fun insertScheduleBucket(schedule: Schedule){
        scheduleBucketList.add(schedule)
        liveScheduleBucket.value = scheduleBucketList
    }

}