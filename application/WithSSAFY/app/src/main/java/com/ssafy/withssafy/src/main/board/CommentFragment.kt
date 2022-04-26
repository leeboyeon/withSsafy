package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCommentBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlin.properties.Delegates

class CommentFragment : BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind, R.layout.fragment_comment) {
    private lateinit var mainActivity: MainActivity

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var mInputMethodManager: InputMethodManager

    // 대댓글 작성 시 필요한 parentId == commentId
    private var parentId = -1
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}