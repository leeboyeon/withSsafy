package com.ssafy.withssafy.src.dto.study

import com.ssafy.withssafy.src.dto.UserX

data class Study(
    val area: String,
    val category: String,
    val content: String,
    val id: Int,
    val isOuting: Int,
    val sbLimit: Int,
    val title: String,
    val user: UserX,
    val writeDateTime: String,
    val photoFile: String
)
