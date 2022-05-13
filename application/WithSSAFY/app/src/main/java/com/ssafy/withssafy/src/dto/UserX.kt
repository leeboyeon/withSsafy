package com.ssafy.withssafy.src.dto

data class UserX(
    val id: Int,
    val name: String,
    val roomId: Int
){
    constructor(id: Int):this(id,"",0)
}