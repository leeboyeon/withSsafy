package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.Study
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.Response

class StudyService {
    suspend fun getStudys():Response<MutableList<Study>> = RetrofitUtil.studyService.getStudys()

    suspend fun insertStudy(study:Study): Response<Any?> = RetrofitUtil.studyService.insertStudy(study)

    suspend fun getStudyById(id:Int) : Response<Study> = RetrofitUtil.studyService.getStudyById(id)
}