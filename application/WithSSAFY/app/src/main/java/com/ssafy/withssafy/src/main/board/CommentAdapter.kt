package com.ssafy.withssafy.src.main.board

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemCommentListBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.viewmodel.BoardViewModel

/**
 * @since 04/27/22
 * @author Jiwoo Choi
 */
class CommentAdapter (val context: Context) : RecyclerView.Adapter<CommentAdapter.ViewHolder>(){
    private val TAG = "CommentAdapter_ws"

    lateinit var commentList: MutableList<Comment>
    lateinit var commentAllList : MutableList<Comment>
    var commentReplyAdapter = ReplyAdapter(context)
    lateinit var dialog: Dialog

    // 게시글 작성자
    var postUserId: Int = -1

    // 현재 로그인한 유저의 아이디
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    inner class ViewHolder(private val binding: ItemCommentListBinding) : RecyclerView.ViewHolder(binding.root) {

        val nick = binding.commentItemTvUserNick
        val moreBtn = binding.commentItemIvMoreBtn
        val addReply = binding.commentItemIbAddReply

        fun bindInfo(comment: Comment) {
            binding.comment = comment

            if(comment.userId == postUserId) {
                nick.setTextColor(Color.parseColor("#2C64BF"))
                nick.text = "익명(글쓴이)"
            }

            binding.executePendingBindings()

            // 대댓글 rv adapter 추가하기
            val replyList = mutableListOf<Comment>()
            for (reply in commentAllList) {
                if(reply.parent == comment.id) {
                    replyList.add(reply)
                }
            }

//            commentReplyAdapter = ReplyAdapter(context)
//                commentNestedAdapter.submitList(list)
            commentReplyAdapter.commentList = replyList
            commentReplyAdapter.postUserId = postUserId

            binding.commentItemRvReply.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                adapter = commentReplyAdapter
                adapter!!.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }

            // 대댓글 수정
            commentReplyAdapter.setModifyItemClickListener(object : ReplyAdapter.MenuClickListener {
                override fun onClick(position: Int, commentId: Int, userId: Int) {
                    replyModifyItemClickListener.onClick(position, commentId, userId)
                }
            })

            // 대댓글 삭제
            commentReplyAdapter.setDeleteItemClickListener(object : ReplyAdapter.MenuClickListener {
                override fun onClick(position: Int, commentId: Int, userId: Int) {
                    replyDeleteItemClickListener.onClick(position, commentId, userId)
                }
            })

            // 대댓글 작성자에게 쪽지 전송
            commentReplyAdapter.setSendNoteItemClickListener(object : ReplyAdapter.MenuClickListener {
                override fun onClick(position: Int, commentId: Int, userId: Int) {
                    replySendNoteItemClickListener.onClick(position, commentId, userId)
                }
            })

            // 대댓글 신고
            commentReplyAdapter.setReportItemClickListener(object : ReplyAdapter.MenuClickListener{
                override fun onClick(position: Int, commentId: Int, userId: Int) {
                    replyReportItemClickListener.onClick(position, commentId, userId)
                }
            })
            

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_comment_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]

        holder.apply {
            bindInfo(comment)
//            setIsRecyclable(false)

            addReply.setOnClickListener {
                addReplyClickListener.onClick(it, nick.text.toString(), position, comment.id)
            }

            moreBtn.setOnClickListener {
                val popup = PopupMenu(context, moreBtn)
                // 작성자인 경우 popup_menu_write 팝업 메뉴
                if(comment.userId == userId) {
                    MenuInflater(context).inflate(R.menu.popup_menu_writer, popup.menu)

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.modify -> {
                                modifyItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.delete -> {
                                deleteItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            else -> {
                                return@setOnMenuItemClickListener false
                            }
                        }
                    }
                } else {    // 작성자가 아닌 경우 popup_menu(쪽지 보내기, 신고)
                    MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.sendNote -> {  // 쪽지 보내기 -> 댓글 작성자 id 필요
                                sendNoteItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.report -> {    // 신고 -> 댓글 작성자 id, 댓글 id
                                reportItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            else -> {
                                return@setOnMenuItemClickListener false
                            }
                        }
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return commentList.size
    }

    interface ItemClickListener {
        fun onClick(view: View, writerNick: String, position: Int, commentId: Int)
    }

    private lateinit var addReplyClickListener : ItemClickListener
    fun setAddReplyItemClickListener(itemClickListener: ItemClickListener) {
        this.addReplyClickListener = itemClickListener
    }


    interface MenuClickListener {
        fun onClick(position: Int, commentId: Int, userId: Int)
    }

    // 댓글 클릭 이벤트
    private lateinit var modifyItemClickListener : MenuClickListener
    fun setModifyItemClickListener(modifyClickListener: MenuClickListener) {
        this.modifyItemClickListener = modifyClickListener
    }

    private lateinit var deleteItemClickListener : MenuClickListener
    fun setDeleteItemClickListener(deleteClickListener: MenuClickListener) {
        this.deleteItemClickListener = deleteClickListener
    }

    private lateinit var sendNoteItemClickListener : MenuClickListener
    fun setSendNoteItemClickListener(sendNoteClickListener: MenuClickListener) {
        this.sendNoteItemClickListener = sendNoteClickListener
    }

    private lateinit var reportItemClickListener : MenuClickListener
    fun setReportItemClickListener(reportClickListener: MenuClickListener) {
        this.reportItemClickListener = reportClickListener
    }

    private lateinit var replyModifyItemClickListener : MenuClickListener
    fun setReplyModifyItemClickListener(replyModifyClickListener: MenuClickListener) {
        this.replyModifyItemClickListener = replyModifyClickListener
    }

    private lateinit var replyDeleteItemClickListener : MenuClickListener
    fun setReplyDeleteItemClickListener(replyDeleteClickListener: MenuClickListener) {
        this.replyDeleteItemClickListener = replyDeleteClickListener
    }

    private lateinit var replySendNoteItemClickListener : MenuClickListener
    fun setReplySendNoteItemClickListener(replySendNoteClickListener: MenuClickListener) {
        this.replySendNoteItemClickListener = replySendNoteClickListener
    }

    private lateinit var replyReportItemClickListener : MenuClickListener
    fun setReplyReportItemClickListener(replyReportClickListener: MenuClickListener) {
        this.replyReportItemClickListener = replyReportClickListener
    }

}