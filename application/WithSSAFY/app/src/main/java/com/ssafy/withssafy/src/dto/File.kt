package com.ssafy.withssafy.src.dto

data class File(
    val absolute: Boolean,
    val absolutePath: String,
    val canonicalPath: String,
    val directory: Boolean,
    val `file`: Boolean,
    val freeSpace: Int,
    val hidden: Boolean,
    val name: String,
    val parent: String,
    val path: String,
    val totalSpace: Int,
    val usableSpace: Int
)