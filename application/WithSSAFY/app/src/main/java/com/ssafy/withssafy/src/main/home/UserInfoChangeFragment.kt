package com.ssafy.withssafy.src.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentUserInfoChangeBinding


class UserInfoChangeFragment : BaseFragment<FragmentUserInfoChangeBinding>(FragmentUserInfoChangeBinding::bind, R.layout.fragment_user_info_change) {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserInfoChangeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}