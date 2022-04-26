package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("/user")
    suspend fun selectUserList(): Response<List<User>>

    // 회원가입
    @POST("/user")
    fun signUp(@Body user:User) : Call<User>

    // ClassRoom
    @GET("/classroom/all")
    suspend fun selectClassRoomList(): Response<MutableList<ClassRoom>>

}