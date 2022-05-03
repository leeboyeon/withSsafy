package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.dto.notice.NoticeRequest
import com.ssafy.withssafy.util.RetrofitUtil

class NoticeService {
    suspend fun selectNoticeList(classRoomId : Int) = RetrofitUtil.noticeApi.selectNoticeList(classRoomId)

    suspend fun insertNotice(noticeReqDto : Notice) = RetrofitUtil.noticeApi.insertNotice(noticeReqDto)

    suspend fun selectNoticeById(id: Int) = RetrofitUtil.noticeApi.selectNoticeById(id)

    suspend fun updateNotice(id: Int, noticeReqDto : NoticeRequest) = RetrofitUtil.noticeApi.updateNotice(id, noticeReqDto)

    suspend fun deleteNoticeById(id: Int) = RetrofitUtil.noticeApi.deleteNoticeById(id)
}