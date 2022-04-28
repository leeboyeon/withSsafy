package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.network.service.MessageService
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel(){
    private val _messageReceive = MutableLiveData<MutableList<Message>>()
    private val _messageSend = MutableLiveData<MutableList<Message>>()

    val messageReceive : LiveData<MutableList<Message>>
        get() = _messageReceive
    val messageSend : LiveData<MutableList<Message>>
        get() = _messageSend

    fun setMessageReceive(list : MutableList<Message>){
        _messageReceive.value = list
    }
    fun setMessageSend(list:MutableList<Message>){
        _messageSend.value = list
    }

    suspend fun getReceiveMessage(userId:Int){
        val response = MessageService().getMessageByUserIdToReceive(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setMessageReceive(res)
                }
            }
        }
    }

    suspend fun getSendMessage(userId:Int){
        val response = MessageService().getMessageByUserIdToSend(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setMessageSend(res)
                }
            }
        }
    }
}