package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.Study
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyApi {
    /**
     * Study Get All
     * @author : LeeBoYeon
     * 모든 스터디목록 불러오기
     */
    @GET("/study-boards")
    suspend fun getStudys() : Response<MutableList<Study>>

    /**
     * Study insert
     * @author : LeeBoYeon
     * 스터디 추가하기
     * */
    @POST("/study-boards")
    suspend fun insertStudy(@Body study:Study) : Response<Any?>

    /**
     * Study Get Detail
     * @author : LeeBoYeon
     * 스터디 ID 값으로 상세정보 불러오기
     * @param : id
     * */
    @GET("/study-boards/{id}")
    suspend fun getStudyById(@Path("id") id:Int):Response<Study>


}