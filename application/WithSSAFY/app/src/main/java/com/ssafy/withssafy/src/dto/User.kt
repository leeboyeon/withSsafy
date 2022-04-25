package com.ssafy.withssafy.src.dto

data class User(
    val id: Int,
    val name: String,
    val userId: String,
    val password: String,
    val studentId: String,
    val classRoomId: Int,
    val state: Int,
    val deviceToken: String
)