package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
<<<<<<< HEAD
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
=======
import com.ssafy.withssafy.src.network.response.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
>>>>>>> 9e18c2383846a60954011c3a4b763cd416d75f74

interface UserApi {

    @GET("/user")
    suspend fun selectUserList(): Response<List<User>>

    // 회원가입
    @POST("/user")
    fun signUp(@Body user:User) : Call<User>

<<<<<<< HEAD
=======
    // 로그인
    @GET("/user/login")
    fun login(@Query("비밀번호") password: String, @Query("아이디") userId: String): Call<UserInfoResponse>

    // 특정 사용자 조회
    @GET("/user/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    // 사용자 계정 삭제
    @DELETE("/user")
    fun deleteUser(@Query("아이디") id: Int) : Call<Boolean>

>>>>>>> 9e18c2383846a60954011c3a4b763cd416d75f74
    // ClassRoom
    @GET("/classroom/all")
    suspend fun selectClassRoomList(): Response<MutableList<ClassRoom>>

}