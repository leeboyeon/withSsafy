package com.ssafy.withssafy.src.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.network.service.BoardService
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
class BoardViewModel : ViewModel() {
    private val TAG = "BoardViewModel_싸피"
    private val _allList = MutableLiveData<MutableList<Board>>()
    
    val allList : LiveData<MutableList<Board>>
        get() = allList
    
    private fun setAllList(list: MutableList<Board>) = viewModelScope.launch { 
        _allList.value = list
    }
    
    suspend fun getAllList() {
        try {
            val response = BoardService().getAllList()
            
            viewModelScope.launch { 
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setAllList(res as MutableList<Board>)
                    } else {
                        Log.e(TAG, "getAllList: ", )
                    }
                } else {
                    Log.e(TAG, "getAllList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllList: ${e.message()}", )
        }
    }
    
}