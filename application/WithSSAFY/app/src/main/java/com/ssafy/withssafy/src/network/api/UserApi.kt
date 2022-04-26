package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.response.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/user")
    suspend fun selectUserList(): Response<List<User>>

    // 회원가입
    @POST("/user")
    fun signUp(@Body user:User) : Call<User>

    // 로그인
    @GET("/user/login")
    fun login(@Query("비밀번호") password: String, @Query("아이디") userId: String): Call<UserInfoResponse>

    // ClassRoom
    @GET("/classroom/all")
    suspend fun selectClassRoomList(): Response<MutableList<ClassRoom>>

}