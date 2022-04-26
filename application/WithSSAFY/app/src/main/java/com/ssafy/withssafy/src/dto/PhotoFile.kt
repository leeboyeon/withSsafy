package com.ssafy.withssafy.src.dto

data class PhotoFile(
    val description: String,
    val `file`: File,
    val filename: String,
    val inputStream: InputStream,
    val `open`: Boolean,
    val outputStream: OutputStream,
    val path: String,
    val readable: Boolean,
    val uri: Uri,
    val url: Url,
    val writable: Boolean
)