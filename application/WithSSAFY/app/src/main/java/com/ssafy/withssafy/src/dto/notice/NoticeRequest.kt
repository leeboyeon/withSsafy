package com.ssafy.withssafy.src.dto.notice

data class NoticeRequest(
    val content: String,
    val filePath: String,
    val photoPath: String,
    val title: String
)