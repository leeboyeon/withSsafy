package com.ssafy.withssafy.src.dto.board

data class Board(
    val id: Int,
    val boardType: BoardType,
    val user: User,
    val title: String,
    val content: String,
    val photoPath: String,
    val writeDateTime: String
)