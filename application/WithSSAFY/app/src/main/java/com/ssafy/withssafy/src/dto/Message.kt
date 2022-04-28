package com.ssafy.withssafy.src.dto

data class Message(
    val content: String,
    val id: Int,
    val send_dt: String,
    val u_fromId: Int,
    val u_toId: Int
)