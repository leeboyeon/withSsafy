package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentPostWriteBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class PostWriteFragment : BaseFragment<FragmentPostWriteBinding>(FragmentPostWriteBinding::bind, R.layout.fragment_post_write) {
    private val TAG = "PostWriteFragment_ws"
    private lateinit var mainActivity : MainActivity

    private lateinit var postPhotoAdapter: PostPhotoAdapter

    // 수정인 경우 넘어오는 postId
    private var postId by Delegates.notNull<Int>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            postId = getInt("postId")
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(postId > 0) {

        } else {

        }
        initPhotoRvAdapter()
    }

    private fun initPhotoRvAdapter() {
        postPhotoAdapter = PostPhotoAdapter()

        // uri observer 내에
//        postPhotoAdapter.photoList = it

        binding.postWriteFragmentRvPhoto.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = postPhotoAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        postPhotoAdapter.setAddClickListener(object : PostPhotoAdapter.AddClickListener {
            override fun onClick(view: View, position: Int) {
                // 갤러리 open
                mainActivity.checkPermissions()
//                if(mainViewModel.photoUriList.value!!.size < 10){
//
//                }else{
//                    showCustomToast("사진을 추가하실 수 없습니다.")
//                }
            }
        })

    }

}