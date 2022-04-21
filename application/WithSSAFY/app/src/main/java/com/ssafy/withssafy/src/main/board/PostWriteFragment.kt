package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentPostWriteBinding
import com.ssafy.withssafy.src.main.MainActivity

class PostWriteFragment : BaseFragment<FragmentPostWriteBinding>(FragmentPostWriteBinding::bind, R.layout.fragment_post_write) {
    private val TAG = "PostWriteFragment_ws"
    private lateinit var mainActivity : MainActivity

    private lateinit var postPhotoAdapter: PostPhotoAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

}