package com.ssafy.withssafy.src.dto

data class FcmRequest(
    val body: String,
    val img: String,
    val title: String,
    val type: Int
)