package com.ssafy.withssafy.util

import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.network.api.StudyApi


class RetrofitUtil {
    companion object{
        val studyService = ApplicationClass.retrofit.create(StudyApi::class.java)
    }
}