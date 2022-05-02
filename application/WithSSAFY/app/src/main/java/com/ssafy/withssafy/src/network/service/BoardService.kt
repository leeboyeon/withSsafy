package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.board.BoardRequest
import com.ssafy.withssafy.util.RetrofitUtil

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
class BoardService {

    suspend fun getAllBoardTType() = RetrofitUtil.boardApi.selectAllBoardType()

    suspend fun getAllList() = RetrofitUtil.boardApi.selectAllList()

    suspend fun addPost(boardRequest: BoardRequest) = RetrofitUtil.boardApi.insertPost(boardRequest)

    suspend fun getPostDetail(postId: Int) = RetrofitUtil.boardApi.selectPostById(postId)

    suspend fun modifyPost(postId: Int, boardRequest: Board) = RetrofitUtil.boardApi.updatePostById(postId, boardRequest)

    suspend fun deletePost(postId: Int) = RetrofitUtil.boardApi.deletePostById(postId)


}