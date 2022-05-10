package com.ssafy.withssafy.src.dto.report

data class ReportRequest(
    val board: Int?,
    val comment: Int?,
    val content: String,
    val id: Int,
    val user: Int
)