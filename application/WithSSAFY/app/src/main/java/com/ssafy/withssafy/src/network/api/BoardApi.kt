package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.board.BoardRequest
import retrofit2.Response
import retrofit2.http.*

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
interface BoardApi {

    /**
     * 모든 게시글 조회
     * boardType dto + 게시글 내용 + user Dto
     */
    @GET("boards")
    suspend fun selectAllList() : Response<List<Board>>

    /**
     * 게시글 등록
     */
    @POST("boards")
    suspend fun insertPost(@Body boardRequest : BoardRequest) : Response<Any?>

    /**
     * 게시글 상세 조회
     * boardType dto + 게시글 내용 + user Dto
     */
    @GET("boards/{id}")
    suspend fun selectPostById(@Path("id") id: Int) : Response<Board>

    /**
     * 게시글 수정
     */
    @PUT("boards/{id}")
    suspend fun updatePostById(@Path("id") id: Int, @Body boardRequest: Board) : Response<Any?>

    /**
     * 게시글 삭제
     */
    @DELETE("boards/{id}")
    suspend fun deletePostById(@Path("id") id: Int) : Response<Any?>


}