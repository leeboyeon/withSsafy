package com.ssafy.withssafy.src.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.service.UserService
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val TAG = "UserViewModel_싸피"
    /**
     * @author Jiwoo
     * USER ViewModel
     */
    private val _allUserList = MutableLiveData<MutableList<User>>()
    private val _loginUserInfo = MutableLiveData<User>()
    private val _classRoomList = MutableLiveData<MutableList<ClassRoom>>()
    private val _isAutoLoginPossible = MutableLiveData<Int>()

    val allUserList : LiveData<MutableList<User>>
        get() = _allUserList

    val loginUserInfo : LiveData<User>
        get() = _loginUserInfo

    val classRommList : LiveData<MutableList<ClassRoom>>
        get() = _classRoomList

    val isAutoLoginPossible : LiveData<Int>
        get() = _isAutoLoginPossible

    private fun setAllUserList(userList : MutableList<User>) = viewModelScope.launch {
        _allUserList.value = userList
    }

    private fun setLoginUserInfo(user: User) = viewModelScope.launch {
        _loginUserInfo.value = user
    }

    private fun setClassRoomList(classRoomList : MutableList<ClassRoom>) = viewModelScope.launch {
        _classRoomList.value = classRoomList
    }

    private fun setIsAutoLoginPossible(possible : Int) {
        _isAutoLoginPossible.value = possible
    }

    suspend fun getAllUserList() {
        val response = UserService().getUserList()
        Log.d(TAG, "getAllUserList: $response")
        viewModelScope.launch {

//
//            if(response.code() == 200 || response.code() == 500) {
//                val res = response.body()
//                if(res != null) {
//                    if(res.success) {
//                        if(res.data["user"] != null && res.message == "회원 정보 조회 성공") {
//                            val type = object : TypeToken<MutableList<User>>() {}.type
//                            val userList: MutableList<User> = CommonUtils.parseDto(res.data["user"]!!, type)
//                            setAllUserList(userList)
//                        } else {
//                            Log.e(TAG, "getAllUserList: ${res.message}", )  // 회원 정보 조회 실패
//                        }
//                    } else {
//                        Log.e(TAG, "getAllUserList: 서버 통신 실패 ${res.message}", )
//                    }
//                }
//            }
        }
    }

    suspend fun getClassRoomList() {
        val response = UserService().getClassRoomList()
        viewModelScope.launch {
            var res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setClassRoomList(res)
                    Log.d(TAG, "getClassRoomList: $res")
                }
            } else {
                Log.d(TAG, "Error : ${response.message()}")
            }
        }
    }

    suspend fun getUser(userId: Int){
        val response = UserService().getUser(userId)
        viewModelScope.launch {
            val res = response.body()
            Log.d(TAG, "getUser: $userId $res")
            if(response.code() == 200) {
                if(res != null) {
                    Log.d(TAG, "getUser: $res")
                    setIsAutoLoginPossible(1)
                } else {
                    setIsAutoLoginPossible(0)
                }
            }
        }
    }

}