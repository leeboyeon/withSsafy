package com.ssafy.withssafy.src.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import com.ssafy.withssafy.util.CommonUtils
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
class BoardViewModel : ViewModel() {
    private val TAG = "BoardViewModel_싸피"


    /**
     * board Table 전체 조회
     */
    private val _allList = MutableLiveData<MutableList<Board>>()

    val allList : LiveData<MutableList<Board>>
        get() = _allList
    
    private fun setAllList(list: MutableList<Board>) = viewModelScope.launch {
        _allList.value = list
    }
    
    suspend fun getAllList() {
        try {
            val response = BoardService().getAllList()
            
            viewModelScope.launch { 
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setAllList(res as MutableList<Board>)
                    } else {
                        Log.e(TAG, "getAllList: ", )
                    }
                } else {
                    Log.e(TAG, "getAllList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllList: ${e.message()}", )
        }
    }

    /**
     * 게시글 상세 조회
     */
    private val _postDetail = MutableLiveData<Board>()

    val postDetail : LiveData<Board>
        get() = _postDetail

    private fun setPostDetail(post: Board) = viewModelScope.launch {
        _postDetail.value = post
    }

    suspend fun getPostDetail(postId: Int) {
        try {
            val response = BoardService().getPostDetail(postId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setPostDetail(res)
                    } else {
                        Log.e(TAG, "getAllList: ", )
                    }
                } else {
                    Log.e(TAG, "getAllList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllList: ${e.message()}", )
        }
    }


    /**
     * 게시글에 해당하는 댓글, 대댓글 리스트
     */
    private val _commentListOnPost = MutableLiveData<MutableList<Comment>>()
    private val _commentList = MutableLiveData<MutableList<Comment>>()

    val commentListOnPost : LiveData<MutableList<Comment>>  // 게시글에 대한 댓글 전체
        get() = _commentListOnPost

    val commentList : LiveData<MutableList<Comment>>    // 댓글 리스트(대댓글 제외)
        get() = _commentList

    private fun setCommentListOnPost(commentList : MutableList<Comment>) = viewModelScope.launch {
        _commentListOnPost.value = commentList
    }

    private fun setCommentList(commentList: MutableList<Comment>) = viewModelScope.launch {
        _commentList.value = commentList
    }

    suspend fun getCommentList(postId : Int) {
        val response = CommentService().getCommentListByBoardId(postId)

        viewModelScope.launch {
            if(response.isSuccessful) {
                val res = response.body()
                if(res != null) {
                    val commentList = res as MutableList<Comment>
                    setCommentListOnPost(commentList)

                    val replyList = mutableListOf<Comment>()
                    for(cmt in commentList) {
                        if(cmt.parent == 0) {
                            replyList.add(cmt)
                        }
                    }
                    setCommentList(replyList)

                }
            }
        }
    }
}