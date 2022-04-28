package com.ssafy.withssafy.src.main.board

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCommentBinding
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import kotlin.properties.Delegates

class CommentFragment : BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind, R.layout.fragment_comment) {
    private val TAG = "CommentFragment_ws"
    private lateinit var mainActivity: MainActivity

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var mInputMethodManager: InputMethodManager

    // 대댓글 작성 시 필요한 parentId == commentId
    private var parentId = -1

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    var lastHeightDiff = 0
    var isOpenKeyboard = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private var postId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getInt("postId")
        }
        mInputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mOnGlobalLayoutListener.onGlobalLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        runBlocking {
            boardViewModel.getCommentList(postId)
        }

        initListener()
        initRecyclerView()
    }

    private fun initListener() {
        backBtnClickEvent()
        layoutListener()
    }

    /**
     * 뒤로가기 버튼 클릭 이벤트
     */
    private fun backBtnClickEvent() {
        binding.commentFragmentIbBack.setOnClickListener {
            this@CommentFragment.findNavController().popBackStack()
        }
    }

    /**
     * 댓글 조회 레이아웃에 키보드 감지 리스너 등록
     */
    private fun layoutListener() {
        binding.commentFragment.viewTreeObserver.addOnGlobalLayoutListener(
            mOnGlobalLayoutListener
        )
    }

    /**
     * 댓글, 대댓글 recyclerView 초기화
     */
    private fun initRecyclerView() {
        commentAdapter = CommentAdapter(requireContext(), boardViewModel)

        binding.commentFragmentRvComment.apply {
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

        // 댓글, 대댓글 작성 클릭 이벤트
        commentAdapter.setAddReplyItemClickListener(object : CommentAdapter.ItemClickListener {
            override fun onClick(view: View, writerNick: String, position: Int, commentId: Int) {
                showKeyboard(binding.commentFragmentEtComment)

                binding.commentFragmentTvWriterNick.visibility = View.VISIBLE
                binding.commentFragmentTvWriterNick.text = writerNick

                parentId = commentId

                insertCommentAndReply()
            }
        })
    }

    /**
     * 댓글 및 대댓글 등록
     * parentId == -1 -> 댓글 등록
     * parentId > 0 -> 대댓글 등록
     */
    private fun insertCommentAndReply() {

        binding.commentFragmentTvConfirm.setOnClickListener {
            val commentContent = binding.commentFragmentEtComment.text.toString()

            if (parentId == -1 && commentContent.isNotEmpty()) {  // 댓글 등록

                val comment = Comment(
                    boardId = postId,
                    userId = userId,
                    content = commentContent
                )

                var response: Response<Any?>

                runBlocking {
                    response = CommentService().addComment(comment)
                }

                if (response.isSuccessful) {

                    showCustomToast("댓글이 등록되었습니다.")
                    runBlocking {
                        boardViewModel.getCommentList(postId)
                    }
                    commentAdapter.notifyDataSetChanged()
                    clearEditTest()
                    clearFocus(mainActivity)
                } else {
                    showCustomToast("댓글 등록 실패")
                    Log.e(TAG, "insertCommentAndReply: ${response.message()}", )
                }
            } else if (parentId != -1 && contentLenChk(commentContent)) {   // 대댓글 작성
                val reply = Comment(
                    parent = parentId,
                    boardId = postId,
                    userId = userId,
                    content = commentContent,
                )

                var response: Response<Any?>

                runBlocking {
                    response = CommentService().addComment(reply)
                }

                if (response.isSuccessful) {

                    showCustomToast("대댓글이 등록되었습니다.")
                    runBlocking {
                        boardViewModel.getCommentList(postId)
                    }
                    commentAdapter.notifyDataSetChanged()

                    clearEditTest()
                    clearFocus(mainActivity)
                } else {
                    showCustomToast("대댓글 등록 실패")
                    Log.e(TAG, "insertCommentAndReply: ${response.message()}",)
                }
            }
        }
    }


    /**
     * content 길이 체크
     */
    private fun contentLenChk(input: String) : Boolean {
        return !(input.trim().isEmpty() || input.length > 255)
    }

    /**
     * 댓글 주인 nick 초기화 및 comment text 초기화
     */
    private fun clearEditTest() {
        binding.commentFragmentTvWriterNick.visibility = View.GONE
        binding.commentFragmentTvWriterNick.text = ""
        binding.commentFragmentEtComment.setText("")
    }










    /**
     * 키보드 UP/DOWN 감지 리스너
     */
    private var mOnGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val activityRootView: View =
            mainActivity.window.decorView.findViewById(android.R.id.content)
        val heightDiff = activityRootView.rootView.height - activityRootView.height
        if (lastHeightDiff == 0) {
            lastHeightDiff = heightDiff
        }
        if (heightDiff > lastHeightDiff) { //keyboard show
            isOpenKeyboard = true
        } else { //keyboard hide
            if (isOpenKeyboard) {
                clearFocus(mainActivity)
                binding.commentFragmentTvWriterNick.visibility = View.GONE
                binding.commentFragmentTvWriterNick.text = ""
                parentId = -1
                isOpenKeyboard = false
            }
        }
    }

    private fun clearFocus(activity: Activity) {
        val v: View = activity.currentFocus ?: return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun showKeyboard(editText: EditText?) {
        if (editText == null) {
            return
        }
        editText.requestFocus()
        mInputMethodManager.showSoftInput(
            editText,
            InputMethodManager.SHOW_FORCED or InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    override fun onStop() {
        super.onStop()
        binding.commentFragment.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}