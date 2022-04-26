package com.ssafy.withssafy.src.dto.board

data class Board(
    val boardType: BoardType,
    val content: String,
    val id: Int,
    val photoPath: String,
    val title: String,
    val user: User,
    val writeDateTime: String
)