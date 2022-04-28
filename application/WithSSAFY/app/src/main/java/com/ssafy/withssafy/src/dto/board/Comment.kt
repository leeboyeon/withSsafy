package com.ssafy.withssafy.src.dto.board

data class Comment(
    val id: Int,
    val parent: Int,
    val boardId: Int,
    val userId: Int,
    val content: String,
    val write_dt: String?
) {
    constructor(boardId: Int, userId: Int, content: String) : this(0, 0, boardId, userId, content, "1651118400")  // 댓글 입력
    constructor(parent: Int, boardId: Int, userId: Int, content: String) : this(0, parent, boardId, userId, content, "1651118400")  // 대댓글 입력
    constructor(id: Int, content: String) : this(id, 0, 0, 0, content, null)
}