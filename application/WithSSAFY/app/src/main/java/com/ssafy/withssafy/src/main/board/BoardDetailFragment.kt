package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardDetailBinding
import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.BoardService
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response

/**
 * @since 04/21/22
 * @author Jiwoo Choi
 * 게시판 내 게시글 리스트 레이아웃
 */

class BoardDetailFragment : BaseFragment<FragmentBoardDetailBinding>(FragmentBoardDetailBinding::bind, R.layout.fragment_board_detail) {
    private val TAG = "BoardDetailFragment_ws"
    private lateinit var mainActivity: MainActivity
    private lateinit var boardDetailAdapter: BoardDetailAdapter

    private val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    private var typeId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            typeId = getInt("typeId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {
            boardViewModel.getBoardListByType(typeId)
            boardViewModel.getUserLikePostList(userId)
        }

        boardViewModel.allBoardType.observe(viewLifecycleOwner) {
            for (item in it) {
                if(typeId == item.id) {
                    binding.boardType = item
                    break
                }
            }
        }
        initListener()
        initRecyclerView()

    }


    private fun initListener() {

        // 뒤로가기 버튼 클릭 이벤트
        binding.boardDetailFragmentIbBack.setOnClickListener {
            this@BoardDetailFragment.findNavController().popBackStack()
        }

        // 글 작성 버튼 클릭 이벤트
        binding.boardDetailFragmentBtnWritePost.setOnClickListener {
            this@BoardDetailFragment.findNavController().navigate(R.id.action_boardDetailFragment_to_postWriteFragment,
                bundleOf("typeId" to typeId)
            )
        }
    }

    /**
     * 게시글 recyclerView 초기화 + rv 아이템 클릭 이벤트
     */
    private fun initRecyclerView() { // 아이템 클릭하면 게시글 상세 화면(PostDetail)으로 이동
        boardDetailAdapter = BoardDetailAdapter(requireContext())

        boardViewModel.boardListByType.observe(viewLifecycleOwner) {
            boardDetailAdapter.postList = it
        }

        boardViewModel.userLikePostList.observe(viewLifecycleOwner) {
            boardDetailAdapter.userLikePost = it
        }

        binding.boardDetailFragmentRvPostList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = boardDetailAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        boardDetailAdapter.setItemClickListener(object : BoardDetailAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, postId: Int, typeId: Int) {
                this@BoardDetailFragment.findNavController().navigate(R.id.action_boardDetailFragment_to_postDetailFragment,
                    bundleOf("postId" to postId, "typeId" to typeId))
            }
        })

        // 게시글 수정 버튼 클릭 이벤트
        boardDetailAdapter.setModifyItemClickListener(object : BoardDetailAdapter.MenuClickListener {
            override fun onClick(position: Int, board: Board) {
                this@BoardDetailFragment.findNavController().navigate(R.id.action_boardDetailFragment_to_postWriteFragment,
                    bundleOf("typeId" to typeId, "postId" to board.id)
                )
            }
        })

        // 게시글 삭제 삭제 클릭 이벤트
        boardDetailAdapter.setDeleteItemClickListener(object : BoardDetailAdapter.MenuClickListener {
            override fun onClick(position: Int, board: Board) {
                deletePost(board.id, position)
            }
        })

        // 게시글 작성자에게 쪽지 보내기 클릭 이벤트
        boardDetailAdapter.setSendNoteItemClickListener(object : BoardDetailAdapter.MenuClickListener {
            override fun onClick(position: Int, board: Board) {

            }
        })

        // 게시글 신고 클릭 이벤트
        boardDetailAdapter.setReportItemClickListener(object : BoardDetailAdapter.MenuClickListener {
            override fun onClick(position: Int, board: Board) {

            }
        })

    }

    /**
     * 게시글 삭제
     */
    private fun deletePost(postId: Int, position: Int) {
        try {
            var response : Response<Any?>
            runBlocking {
                response = BoardService().deletePost(postId)
            }
            if(response.isSuccessful) {
                showCustomToast("게시글이 삭제되었습니다.")
                runBlocking {
                    boardViewModel.getBoardListByType(typeId)
                }
                boardDetailAdapter.notifyItemRemoved(position)
            } else {
                showCustomToast("게시글 삭제 실패")
                Log.d(TAG, "deletePost: ${response.message()}", )
            }
        } catch (e: HttpException) {
            Log.e(TAG, "deletePost: ${e.response()}", )
        }
    }
}