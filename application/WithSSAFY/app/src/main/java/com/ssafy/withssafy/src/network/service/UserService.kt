package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.util.RetrofitCallback
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    suspend fun getUserList() = RetrofitUtil.userService.selectUserList()

    suspend fun getClassRoomList() = RetrofitUtil.userService.selectClassRoomList()

    // 회원가입
    fun signUp(user: User, callback: RetrofitCallback<User>) {
        RetrofitUtil.userService.signUp(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                if(response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}