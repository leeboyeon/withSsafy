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
    private val _recruit = MutableLiveData<Recruit>()
    private val _likeRecruitList = MutableLiveData<MutableList<Recruit>>()

    val recruitList : LiveData<MutableList<Recruit>>
        get() = _recruitList

    val recruit : LiveData<Recruit>
        get() = _recruit

    val likeRecruitList : LiveData<MutableList<Recruit>>
        get() = _likeRecruitList

    fun setRecruitList(list: MutableList<Recruit>) {
        _recruitList.value = list
    }

    fun setRecruit(recruit : Recruit) {
        _recruit.value = recruit
    }

    fun setLikeRecruitList(list: MutableList<Recruit>) {
        _likeRecruitList.value = list
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

    suspend fun getRecruit(id: Int) {
        val response = RecruitService().selectRecruitById(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setRecruit(res)
                }
            }
        }
    }

    suspend fun getLikeRecruitList(userId : Int) {
        val response = RecruitService().likeRecruitList(userId)
        viewModelScope.launch {
            val res =response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setLikeRecruitList(res)
                }
            }
        }
    }
}