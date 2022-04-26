package com.ssafy.withssafy.src.main.board

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentPostDetailBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Comment
import retrofit2.Response
import kotlin.properties.Delegates

class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::bind, R.layout.fragment_post_detail) {
    private val TAG = "PostDetailFragment_ws"
    private lateinit var mainActivity: MainActivity

    private var postId = -1
    private var typeId = -1


    private lateinit var commentAdapter: CommentAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            postId = getInt("postId")
            typeId = getInt("typeId")
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        runBlocking {
//            mainViewModel.getCommentList(postId)
        }
        initDataBinding()
        initListener()
        initCommentRv()

    }

    private fun initDataBinding() {
//        binding.mainViewModel = mainViewModel
//        binding.loginUser = mainViewModel.loginUserInfo.value
    }

    private fun initListener() {
        backBtnClickEvent()
        commentLayoutClickEvent()
    }
    /**
     * 뒤로가기 버튼 클릭 이벤트
     */
    private fun backBtnClickEvent() {
        binding.postDetailFragmentIbBack.setOnClickListener {
            this@PostDetailFragment.findNavController().popBackStack()
        }
    }

    /**
     * 댓글 전체 화면 클릭 이벤트
     */
    private fun commentLayoutClickEvent() {
        binding.postDetailFragmentClCommentAll.setOnClickListener {
            this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment)
        }
    }

    /**
     * 댓글 recyclerView 초기화
     */
    private fun initCommentRv() {

        binding.postDetailFragmentRvComment.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        commentAdapter = CommentAdapter(requireContext(), mainViewModel)

//        mainViewModel.commentListWoParents.observe(viewLifecycleOwner, {
//
//            commentAdapter.commentList = it
//
//            mainViewModel.commentAllList.observe(viewLifecycleOwner, { all ->
//                commentAdapter.commentAllList = all
//            })
//
//            localCommentAdapter.userList = mainViewModel.allUserList.value!!

        commentAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.postDetailFragmentRvComment.adapter = commentAdapter

    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }

}