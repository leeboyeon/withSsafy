package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentPostWriteBinding
import com.ssafy.withssafy.src.dto.board.BoardRequest
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.BoardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * @since 04/21/22
 * @author Jiwoo Choi
 * 게시글 작성 레이아웃
 */
class PostWriteFragment : BaseFragment<FragmentPostWriteBinding>(FragmentPostWriteBinding::bind, R.layout.fragment_post_write) {
    private val TAG = "PostWriteFragment_ws"
    private lateinit var mainActivity : MainActivity

    private lateinit var postPhotoAdapter: PostPhotoAdapter

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    private var typeId by Delegates.notNull<Int>()
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
            typeId = getInt("typeId")
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(postId > 0) {
            // 데이터 세팅
//            modifyPostBtnClickEvent()
        } else {
            registerPostBtnClickEvent()
        }

        initListener()
        initPhotoRvAdapter()
    }

    private fun initListener() {
        mainActivity.runOnUiThread(kotlinx.coroutines.Runnable {
            inputObservable()
        })
    }

    /**
     * 게시글 등록 버튼 클릭
     * 중복 클릭 방지 ThrottleClick 클릭
     */
    private fun registerPostBtnClickEvent() {
        binding.postWriteFragmentBtnConfirm.onThrottleClick {
            val title = binding.postWriteFragmentEtPostTitle.text.toString()    // max 255
            val content = binding.postWriteFragmentTietPostContent.text.toString() // max 500
            // 삭제해야되는 부분
            typeId = 1
            if(contentLenChk(content) && titleLenChk(title) && typeId != -1) {
                val post = BoardRequest(
                    typeId = typeId,
                    userId = userId,
                    title = title,
                    content = content,
                    photoPath = "")
//                photoPath = photoPath)


                registerPost(post)
            } else {
                showCustomToast("입력 값을 확인해 주세요.")
            }
        }.addDisposable()
    }

    private fun registerPost(post : BoardRequest) {
        var response : Response<Any?>

        runBlocking {
            response = BoardService().addPost(post)
        }

        if(response.isSuccessful) {
            showCustomToast("게시글 등록이 완료되었습니다.")
            this@PostWriteFragment.findNavController().popBackStack()
        } else {
            showCustomToast("게시글 등록에 실패했습니다.")
        }
    }



    /**
     * 사진 선택 recycler view init
     */
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

    /**
     * content editText query debounce
     */
    private fun inputObservable() {
        binding.postWriteFragmentTietPostContent.setQueryDebounce {
            contentLenChk(it)
        }.addDisposable()
    }


    /**
     * title 길이 체크
     */
    private fun titleLenChk(input: String) : Boolean {
        return !(input.trim().isEmpty() || input.length > 255)
    }

    /**
     * content 길이 검사
     */
    private fun contentLenChk(input: String) : Boolean {
        binding.postWriteFragmentTvContentLen.text = "(${input.length} / 500)"
        if(input.trim().isEmpty()){
            binding.postWriteFragmentTilPostContent.error = "Required Field"
            binding.postWriteFragmentTietPostContent.requestFocus()
            return false
        } else if(input.length < 10 || input.length > 500) {
            binding.postWriteFragmentTilPostContent.error = "작성된 내용의 길이를 확인해 주세요."
            binding.postWriteFragmentTietPostContent.requestFocus()
            return false
        }
        else {
            binding.postWriteFragmentTilPostContent.error = null
            return true
        }
    }

    /**
     * RxBinding의 Throttle 기능 사용하는 Button 함수
     * @param throttleSecond 해당 시간동안 중복 클릭 방지 (기본으로 1초)
     * @param subscribe 클릭 리스너 정의
     * @since 04/26/22
     * @author Jiwoo Choi
     */
    fun Button.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
        .throttleFirst(throttleSecond, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            subscribe?.invoke()
        }

    /**
     * EditText 쿼리 디바운싱 함수 적용
     * @param queryFunction
     * @since 04/26/22
     * @author Jiwoo Choi
     */
    fun EditText.setQueryDebounce(queryFunction: (String) -> Unit) = textChanges()
        // 마지막 글자 입력 0.5초 후에 onNext 이벤트로 데이터 발행
        .debounce(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        // 구독을 통해 이벤트 응답 처리
        .subscribeBy(
            onNext = {
                Log.d(TAG, "onNext : $it")
                queryFunction(it.toString())
            },
            onComplete = {
                Log.d(TAG, "onComplete")
            },
            onError = {
                Log.i(TAG, "onError : $it")
            }
        )



//    inner class OnThrottleClickListener(private val clickListener: View.OnClickListener, private val interval: Long = 300) : View.OnClickListener {
//
//        private var clickable = true
//        // clickable 플래그를 이 클래스가 아니라 더 상위 클래스에 두면
//        // 여러 뷰에 대한 중복 클릭 방지할 수 있다.
//
//        override fun onClick(v: View?) {
//            if (clickable) {
//                clickable = false
//                v?.run {
//                    postDelayed({
//                        clickable = true
//                    }, interval)
//                    clickListener.onClick(v)
//                }
//            } else {
//                Log.d(TAG, "waiting for a while")
//            }
//        }
//    }
}