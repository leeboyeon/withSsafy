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

    private var postId by Delegates.notNull<Int>()

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var mInputMethodManager: InputMethodManager

    // 대댓글 작성 시 필요한 parentId == commentId
    private var parentId = -1
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            postId = getInt("postId")
        }
        mInputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mOnGlobalLayoutListener.onGlobalLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mainActivity.hideBottomNavi(true)
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)

        runBlocking {
//            mainViewModel.getCommentList(postId)
        }
        initDataBinding()
        backBtnClickEvent()
        initCommentRv()

        // 키보드 감지 리스너 등록
        binding.postDetailFragmentCl.viewTreeObserver.addOnGlobalLayoutListener(
            mOnGlobalLayoutListener
        )

        insertCommentAndReply()    // default : 댓글 등록
    }


    private fun initDataBinding() {
//        binding.mainViewModel = mainViewModel
//        binding.loginUser = mainViewModel.loginUserInfo.value
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
//
//            // 답글달기 버튼 클릭 이벤트
//            localCommentAdapter.setAddReplyItemClickListener(object :
//                LocalCommentAdapter.ItemClickListener {
//                override fun onClick(view: TextView, position: Int, commentId: Int) {
//                    // 대댓글 작성
//                    // 클릭한 댓글 뷰 색상 바꿔주고
//                    // 하단 댓글 작성 부분 focus
//                    // 게시 버튼 눌렀을 때 글자가 하나라도 쓰여져있으면 insert
////                    val mInputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////                    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
////                    binding.localCmtFragmentEtComment.requestFocus()
//
//                    showKeyboard(binding.localCmtFragmentEtComment)
//
//                    binding.localCmtFragmentTvWriterNick.visibility = View.VISIBLE
//                    binding.localCmtFragmentTvWriterNick.text = "@${it[position].nickname}"
//
//                    parentId = it[position].id
//
//                    insertCommentAndReply()
//
//                }
//            })
//
//            // 수정 클릭 이벤트
//           commentAdapter.setModifyItemClickListener(object :
//                CommentAdapter.MenuClickListener {
//                override fun onClick(commentId: Int, position: Int) {
//                    // 댓글 수정
//                    // 수정 클릭한 댓글 뷰 색상 변경하고,는 안되겠넹
//                    // 기존 댓글 내용 editText에 세팅
//                    showKeyboard(binding.localCmtFragmentEtComment)
//
//                    binding.localCmtFragmentEtComment.setText(it[position].comment)
//
//                    updateComment(commentId)
//
//                }
//            })
//        })

        commentAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.postDetailFragmentRvComment.adapter = commentAdapter


        commentAdapter.setDeleteItemClickListener(object :
            CommentAdapter.MenuClickListener {
            override fun onClick(commentId: Int, position: Int) {
                deleteComment(commentId, position)
            }
        })
    }

    /**
     * 댓글 삭제 response
     */
    private fun deleteComment(commentId: Int, position: Int) {
//        Log.d(TAG, "deleteComment: $commentId")
//        var response: Response<Message>
//        runBlocking {
//            response = BoardService().deleteComment(commentId)
//        }
//        if (response.code() == 200 || response.code() == 500) {
//            val res = response.body()
//            if (res != null) {
//                if (res.success == true && res.data["isSuccess"] == true) {
//                    Log.d(TAG, "deleteComment: 댓글 삭제 완료")
//                    showCustomToast("댓글이 삭제되었습니다.")
//                    runBlocking {
//                        mainViewModel.getCommentList(postId)
//                    }
////                    localCommentAdapter.notifyItemRemoved(position)
//                    commentAdapter.notifyDataSetChanged()
//                } else {
//                    showCustomToast("댓글 삭제 실패")
//                }
//            }
//        }
    }

    /**
     * 댓글 및 대댓글 등록
     * parentId == -1 -> 댓글 등록
     * parentId > 0 -> 대댓글 등록
     */
    private fun insertCommentAndReply() {

        binding.postDetailFragmentTvConfirm.setOnClickListener {
//            val commentContent = binding.postDetailFragmentEtComment.text.toString()
//            if (parentId != -1 && commentContent.isNotEmpty()) {   // 대댓글 작성
//                val reply = Comment(
//                    boardId = postId,
//                    comment = commentContent,
//                    parent = parentId,
//                    userId = userId
//                )
//
//                var response: Response<Message>
//
//                runBlocking {
//                    response = BoardService().insertReply(reply)
//                }
//
//                if (response.code() == 200 || response.code() == 500) {
//                    val res = response.body()
//                    if (res != null) {
//                        if (res.success == true && res.data["isSuccess"] == true) {
//                            showCustomToast("대댓글이 등록되었습니다.")
//                            runBlocking {
//                                mainViewModel.getCommentList(postId)
//                            }
//                            commentAdapter.notifyDataSetChanged()
//                            binding.postDetailFragmentTvWriterNick.visibility = View.GONE
//                            binding.postDetailFragmentTvWriterNick.text = ""
//                            binding.postDetailFragmentEtComment.setText("")
////
//                            clearFocus(mainActivity)
//                        } else {
//                            Log.e(TAG, "insertCommentAndReply: ${res.message}",)
//                            showCustomToast("대댓글 등록 실패")
//                        }
//                    }
//                }
//            } else if (parentId == -1 && commentContent.isNotEmpty()) {  // 댓글 등록
//
//                val comment = Comment(
//                    boardId = postId,
//                    comment = commentContent,
//                    userId = userId,
//                    nickname = mainViewModel.loginUserInfo.value!!.nickname
//                )
//
//                var response: Response<Message>
//
//                runBlocking {
//                    response = BoardService().insertComment(comment)
//                }
//
//                if (response.code() == 200 || response.code() == 500) {
//                    val res = response.body()
//                    if (res != null) {
//                        if (res.success == true && res.data["isSuccess"] == true) {
//                            showCustomToast("댓글이 등록되었습니다.")
//                            runBlocking {
//                                mainViewModel.getCommentList(postId)
//                            }
//                            commentAdapter.notifyDataSetChanged()
//                            binding.postDetailFragmentTvWriterNick.visibility = View.GONE
//                            binding.postDetailFragmentTvWriterNick.text = ""
//                            binding.postDetailFragmentEtComment.setText("")
////
//                            clearFocus(mainActivity)
//                        } else {
//                            Log.e(TAG, "insertCommentAndReply: ${res.message}",)
//                            showCustomToast("댓글 등록 실패")
//                        }
//                    }
//                }
//            }


        }
    }

    /**
     * 댓글 수정 response
     */
    private fun updateComment(commentId: Int) {

        binding.postDetailFragmentTvConfirm.setOnClickListener {
//            val commentContent = binding.postDetailFragmentEtComment.text.toString()
//            if(commentContent.isNotEmpty() && commentId > 0) {
//
//                val updateComment = Comment(commentId, commentContent)
//
//                var response: Response<Message>
//
//                runBlocking {
//                    response = BoardService().updateComment(updateComment)
//                }
//
//                if (response.code() == 200 || response.code() == 500) {
//                    val res = response.body()
//                    if (res != null) {
//                        if (res.success == true && res.data["isSuccess"] == true) {
//                            showCustomToast("댓글이 수정되었습니다.")
//                            runBlocking {
//                                mainViewModel.getCommentList(postId)
//                            }
//                            commentAdapter.notifyDataSetChanged()
//                            binding.postDetailFragmentTvWriterNick.visibility = View.GONE
//                            binding.postDetailFragmentTvWriterNick.text = ""
//                            binding.postDetailFragmentEtComment.setText("")
//                            clearFocus(mainActivity)
//                        } else {
//                            showCustomToast("댓글 수정 실패")
//                            Log.e(TAG, "updateComment: ${res.message}",)
//                        }
//                    }
//                }
//            }
        }

    }


    /**
     * 키보드 UP/DOWN 감지 리스너
     */
    var lastHeightDiff = 0
    var isOpenKeyboard = false
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
                binding.postDetailFragmentTvWriterNick.visibility = View.GONE
                binding.postDetailFragmentTvWriterNick.text = ""
                parentId = -1
                isOpenKeyboard = false
            }
        }
    }

    private fun clearFocus(activity: Activity) {
        val v: View = activity.getCurrentFocus() ?: return
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
        binding.postDetailFragmentCl.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    override fun onDestroy() {
        super.onDestroy()
//        mainActivity.hideBottomNavi(false)
    }

}