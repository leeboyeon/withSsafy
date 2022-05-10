package com.ssafy.withssafy.src.dto.report

data class Report(
    val board: Board?,
    val comment: Comment?,
    val content: String,
    val id: Int,
    val user: User
)