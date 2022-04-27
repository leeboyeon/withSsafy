package com.ssafy.withssafy.src.main.board

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
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

class CommentAdapter (val context: Context/*, val mainViewModel: MainViewModels*/) : RecyclerView.Adapter<CommentAdapter.ViewHolder>(){
    private val TAG = "CommentAdapter_ws"

    lateinit var commentList: MutableList<Comment>
    lateinit var commentAllList : MutableList<Comment>
    lateinit var commentReplyAdapter : ReplyAdapter
    lateinit var dialog: Dialog

    // 현재 로그인한 유저의 아이디
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    inner class ViewHolder(private val binding: ItemCommentListBinding) : RecyclerView.ViewHolder(binding.root) {

        val moreBtn = binding.commentItemIvMoreBtn
        val addReply = binding.commentItemIbAddReply

        fun bindInfo(comment: Comment) {

            binding.comment = comment
            binding.executePendingBindings()

            // 대댓글 rv adapter 추가하기
            val replyList = mutableListOf<Comment>()
            for (reply in commentAllList) {
                if(reply.parent == comment.id) {
                    replyList.add(reply)
                }
            }

            commentReplyAdapter = ReplyAdapter(context)
//                commentNestedAdapter.submitList(list)
            commentReplyAdapter.commentList = replyList
            binding.commentItemRvReply.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                adapter = commentReplyAdapter
                adapter!!.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }

//            commentReplyAdapter.setModifyItemClickListener(object : ReplyAdapter.MenuClickListener {
//
//                override fun onClick(commentId: Int, postId: Int, position: Int) {
//                    // 대댓글 수정
//                    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_reply,null)
//                    if(dialogView.parent!=null){
//                        (dialogView.parent as ViewGroup).removeAllViews()
//                    }
//                    dialog = Dialog(context)
//                    dialog.setContentView(dialogView)
//                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//                    dialogView.findViewById<TextView>(R.id.updateReplyDialog_tvContent).text = replyList[position].comment
//
//                    dialog.show()
//
//                    dialogView.findViewById<Button>(R.id.updateReplyDialog_btnCancel).setOnClickListener {
//                        dialog.dismiss()
//                    }
//
//                    dialogView.findViewById<AppCompatButton>(R.id.updateReplyDialog_btnOk).setOnClickListener {
//                        updateReply(commentId, dialogView.findViewById<TextView>(R.id.updateReplyDialog_tvContent).text.toString(), postId)
//                    }
//
//                }
//            })

            commentReplyAdapter.setDeleteItemClickListener(object : ReplyAdapter.MenuClickListener {

                override fun onClick(commentId: Int, postId: Int, position: Int) {
//                    deleteReply(commentId, postId, position)
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
                addReplyClickListener.onClick(it as TextView, position, comment.id)
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
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.delete -> {
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
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
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.report -> {    // 신고 -> 댓글 작성자 id, 댓글 id
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
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
        fun onClick(view: TextView, position: Int, commentId: Int)
    }

    private lateinit var addReplyClickListener : ItemClickListener
    fun setAddReplyItemClickListener(itemClickListener: ItemClickListener) {
        this.addReplyClickListener = itemClickListener
    }


    interface MenuClickListener {
        fun onClick(position: Int, commentId: Int, userId: Int)
    }

    private lateinit var menuItemClickListener : MenuClickListener
    fun setMenuItemClickListener(menuClickListener: MenuClickListener) {
        this.menuItemClickListener = menuClickListener
    }


//    /**
//     * 대댓글 삭제 response
//     */
//    private fun deleteReply(commentId: Int, postId: Int, position: Int) {
//        var response: Response<Message>
//        runBlocking {
//            response = BoardService().deleteComment(commentId)
//        }
//        if (response.code() == 200 || response.code() == 500) {
//            val res = response.body()
//            if (res != null) {
//                if (res.success == true && res.data["isSuccess"] == true) {
//                    Toast.makeText(context, "대댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
//
//                    runBlocking {
//                        mainViewModel.getCommentList(postId)
//                    }
////                    localCommentAdapter.notifyItemRemoved(position)
////                    commentReplyAdapter.notifyDataSetChanged()
//                    commentReplyAdapter.notifyItemRemoved(position)
//                    notifyDataSetChanged()
//                } else {
//                    Toast.makeText(context, "대댓글이 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    Log.e(TAG, "deleteReply: ${res.message}", )
//                }
//            }
//        }
//    }
//
//    /**
//     * 대댓글 수정 response
//     */
//    private fun updateReply(commentId: Int, content: String, postId: Int) {
//
//        if(content.isNotEmpty() && commentId > 0) {
//
//            val updateComment = Comment(commentId, content)
//
//            var response: Response<Message>
//
//            runBlocking {
//                response = BoardService().updateComment(updateComment)
//            }
//
//            if (response.code() == 200 || response.code() == 500) {
//                val res = response.body()
//                if (res != null) {
//                    if (res.success == true && res.data["isSuccess"] == true) {
//                        Toast.makeText(context, "대댓글이 수정되었습니다.", Toast.LENGTH_SHORT).show()
//
//                        runBlocking {
//                            mainViewModel.getCommentList(postId)
//                        }
//                        dialog.dismiss()
//                        commentReplyAdapter.notifyDataSetChanged()
//                        notifyDataSetChanged()
//                    } else {
//                        Toast.makeText(context, "대댓글이 수정 실패", Toast.LENGTH_SHORT).show()
//
//                        Log.e(TAG, "updateComment: ${res.message}",)
//                    }
//                }
//            }
//        }
//    }
}