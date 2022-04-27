package com.ssafy.withssafy.src.dto.board

data class Comment(
    val id: Int,
    val parent: Int,
    val boardId: Int,
    val userId: Int,
    val content: String,
    val write_dt: String
)