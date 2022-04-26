package com.ssafy.withssafy.src.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Study
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.launch

private const val TAG = "TeamViewModel"
class TeamViewModel : ViewModel(){
    lateinit var uploadedImage: Bitmap
    var uploadImageUri: Uri? = null


    private val _studyList = MutableLiveData<MutableList<Study>>()
    private val _study = MutableLiveData<Study>()

    val studyList:LiveData<MutableList<Study>>
        get() = _studyList
    val study : LiveData<Study>
        get() = _study

    fun setStudyList(list: MutableList<Study>){
        _studyList.value = list
    }
    fun setStudy(data:Study){
        _study.value = data
    }

    suspend fun getStudys(){
        val response = StudyService().getStudys()
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    Log.d(TAG, "getStudys: $res")
                    setStudyList(res)
                }
            }
        }
    }
    suspend fun getStudy(id:Int){
        val response = StudyService().getStudyById(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setStudy(res)
                }
            }
        }
    }
}