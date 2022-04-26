package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.util.RetrofitUtil

class UserService {
    suspend fun getUserList() = RetrofitUtil.userService.selectUserList()
}