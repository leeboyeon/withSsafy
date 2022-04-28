package com.ssafy.withssafy.util

import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.network.api.*


class RetrofitUtil {
    companion object{
        val studyService = ApplicationClass.retrofit.create(StudyApi::class.java)
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
        val boardApi = ApplicationClass.retrofit.create(BoardApi::class.java)
        val commentApi = ApplicationClass.retrofit.create(CommentApi::class.java)
        val messageApi = ApplicationClass.retrofit.create(MessageApi::class.java)
    }
}