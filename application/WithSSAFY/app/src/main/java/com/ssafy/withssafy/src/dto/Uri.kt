package com.ssafy.withssafy.src.dto

data class Uri(
    val absolute: Boolean,
    val authority: String,
    val fragment: String,
    val host: String,
    val opaque: Boolean,
    val path: String,
    val port: Int,
    val query: String,
    val rawAuthority: String,
    val rawFragment: String,
    val rawPath: String,
    val rawQuery: String,
    val rawSchemeSpecificPart: String,
    val rawUserInfo: String,
    val scheme: String,
    val schemeSpecificPart: String,
    val userInfo: String
)