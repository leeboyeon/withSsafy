package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.withssafy.src.dto.Study

class TeamViewModel : ViewModel(){
    private val _studyList = MutableLiveData<MutableList<Study>>()

    val studyList:LiveData<MutableList<Study>>
        get() = _studyList

    fun setStudyList(list: MutableList<Study>){
        _studyList.value = list
    }

}