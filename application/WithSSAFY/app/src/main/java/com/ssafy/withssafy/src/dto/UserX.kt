package com.ssafy.withssafy.src.dto

data class UserX(
    val id: Int,
    val name: String
){
    constructor(id: Int):this(id,"")
}