package com.ssafy.withssafy.util

import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.network.api.StudyApi
import com.ssafy.withssafy.src.network.api.UserApi


class RetrofitUtil {
    companion object{
        val studyService = ApplicationClass.retrofit.create(StudyApi::class.java)
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
    }
}