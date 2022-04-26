package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Study
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel(){
    private val _studyList = MutableLiveData<MutableList<Study>>()

    val studyList:LiveData<MutableList<Study>>
        get() = _studyList

    fun setStudyList(list: MutableList<Study>){
        _studyList.value = list
    }

    suspend fun getStudys(){
        val response = StudyService().getStudys()
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setStudyList(res)
                }
            }
        }
    }
}