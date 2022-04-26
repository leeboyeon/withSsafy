package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("/user")
    suspend fun selectUserList(): Response<List<User>>


    // ClassRoom
    @GET("/classroom/all")
    suspend fun selectClassRoomList(): Response<MutableList<ClassRoom>>

}