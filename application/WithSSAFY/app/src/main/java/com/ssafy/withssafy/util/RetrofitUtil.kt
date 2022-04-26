package com.ssafy.withssafy.util

import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.network.api.BoardApi
import com.ssafy.withssafy.src.network.api.UserApi
import retrofit2.create


class RetrofitUtil {
    companion object {
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
        val boardApi = ApplicationClass.retrofit.create(BoardApi::class.java)
    }
}