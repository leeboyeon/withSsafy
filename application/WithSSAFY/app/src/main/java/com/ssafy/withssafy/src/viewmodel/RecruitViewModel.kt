package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.network.service.RecruitService
import kotlinx.coroutines.launch

class RecruitViewModel : ViewModel(){
    private val _recruitList = MutableLiveData<MutableList<Recruit>>()

    val recruitList : LiveData<MutableList<Recruit>>
        get() = _recruitList

    fun setRecruitList(list: MutableList<Recruit>) {
        _recruitList.value = list
    }

    suspend fun getRecruitList() {
        val response = RecruitService().selectRecruitAll()
        viewModelScope.launch {
            val res =response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setRecruitList(res)
                }
            }
        }
    }
}