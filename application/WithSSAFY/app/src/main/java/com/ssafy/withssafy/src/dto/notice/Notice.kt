package com.ssafy.withssafy.src.dto.notice

data class Notice(
    val classRoomId: Int,
    val content: String,
    val filePath: String,
    val photoPath: String,
    val title: String,
    val typeId: Int,
    val userId: Int
)