package com.ssafy.withssafy.src.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.network.service.NoticeService
import kotlinx.coroutines.launch

private const val TAG = "NoticeViewModel"
class NoticeViewModel : ViewModel() {
    private val _noticeAllList = MutableLiveData<MutableList<Notice>>()
    private val _classRoomId = MutableLiveData<Int>()
    var _uploadImageUri = MutableLiveData<Uri?>()
    //var uploadImageUri: Uri? = null

    val noticeAllList : LiveData<MutableList<Notice>>
        get() = _noticeAllList

    val classRoomId : LiveData<Int>
        get() = _classRoomId

    val uploadImageUri : LiveData<Uri?>
        get() = _uploadImageUri

    fun setNoticeList(noticeList : MutableList<Notice>) {
        _noticeAllList.value = noticeList
    }

    fun setClassRoomId(id : Int) {
        _classRoomId.value = id
    }

    fun setUploadImageUri(uri: Uri?) {
        _uploadImageUri.value = uri
    }

    suspend fun getNoticeList(classRoomId : Int) {
        val response = NoticeService().selectNoticeList(classRoomId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setNoticeList(res)
                    Log.d(TAG, "getNoticeList: $classRoomId $res")
                }
            }
        }
    }
}