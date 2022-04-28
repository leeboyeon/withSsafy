package com.ssafy.withssafy.src.main.board

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentPostDetailBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking


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
            postId = 54
            boardViewModel.getPostDetail(postId)
            boardViewModel.getCommentList(postId)

        }

        initDataBinding()
        initListener()
        initCommentRecyclerView()

    }

    private fun initDataBinding() {
        boardViewModel.postDetail.observe(viewLifecycleOwner) {
            binding.post = it
        }
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

//        binding.postDetailFragmentClCommentAll.setOnTouchListener { v, event ->
//            binding.postDetailFragmentScrollview.requestDisallowInterceptTouchEvent(true)
//            return@setOnTouchListener false
//        }


        binding.postDetailFragmentClCommentAll.setOnClickListener {
            this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                bundleOf("postId" to postId))
        }
    }

    /**
     * 댓글 recyclerView 초기화
     */
    private fun initCommentRecyclerView() {

        commentAdapter = CommentAdapter(requireContext(), boardViewModel)

        binding.postDetailFragmentRvComment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        boardViewModel.commentList.observe(viewLifecycleOwner) {
            commentAdapter.commentList = it
        }

        boardViewModel.commentListOnPost.observe(viewLifecycleOwner) {
            commentAdapter.commentAllList = it
        }

        commentAdapter.postUserId = boardViewModel.postDetail.value!!.user.id

    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }

}