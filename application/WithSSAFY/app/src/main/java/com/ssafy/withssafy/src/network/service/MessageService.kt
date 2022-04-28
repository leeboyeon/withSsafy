package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.Response

class MessageService {

    suspend fun insertMessage(message:Message) : Response<Any?> = RetrofitUtil.messageApi.insertMessage(message)

    suspend fun getMessageByUserIdToReceive(id:Int) : Response<MutableList<Message>> = RetrofitUtil.messageApi.getMessageByUserIdToReceive(id)

    suspend fun getMessageByUserIdToSend(id: Int) : Response<MutableList<Message>> = RetrofitUtil.messageApi.getMessageByUserIdToSend(id)

}