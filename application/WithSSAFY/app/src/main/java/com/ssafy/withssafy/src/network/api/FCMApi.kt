package com.ssafy.withssafy.src.network.api

import retrofit2.Response
import retrofit2.http.*

interface FCMApi {

    // Token 정보 서버로 전송
    @POST("/fcm/token")
    suspend fun uploadToken(@Query("token") token: String, @Query("userId") userId: Int): Response<Any?>
//
//    @POST("fcm/sendMessageTo")
//    suspend fun sendMessageTo(@Body fcmParamDto: Fcm): Response<Message>
//
//    @POST("fcm/broadcast")
//    suspend fun broadCast(@Body fcmParamDto: Fcm) : Response<Message>
}