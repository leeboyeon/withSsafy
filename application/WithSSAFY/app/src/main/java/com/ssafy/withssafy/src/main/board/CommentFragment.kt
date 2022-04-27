package com.ssafy.withssafy.src.main.board

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCommentBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class CommentFragment : BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind, R.layout.fragment_comment) {
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
        binding.commentFragment.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}