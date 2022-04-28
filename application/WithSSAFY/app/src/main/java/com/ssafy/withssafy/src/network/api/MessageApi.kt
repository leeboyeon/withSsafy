package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.Message
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageApi {
    /**
     * Message All Get
     * 모든 메시지를 가져온다
     * @author : LeeBoYeon
     * */
    @GET("/message")
    suspend fun getAllMessage() : Response<MutableList<Message>>

    /**
     * Message Insert
     * 메시지를 보낸다
     * @author : LeeBoYeon
     * */
    @POST("/message")
    suspend fun insertMessage(@Body messageDto:Message) : Response<Any?>

    /**
     * Message Get By UserId to Receive
     * 유저아이디에 해당하는 받은 메시지들을 불러온다
     * @author : LeeBoYeon
     * */
    @GET("/message/receive")
    suspend fun getMessageByUserIdToReceive(@Query("id")id:Int) : Response<MutableList<Message>>

    /**
     * Message Get By UserId to Send
     * 유저아이디에 해당하는 보낸 메시지들을 불러온다
     * @author : LeeBoYeon
     * */
    @GET("/message/send")
    suspend fun getMessageByUserIdToSend(@Query("id")id:Int) : Response<MutableList<Message>>
}