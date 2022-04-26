package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemReplyListBinding
import com.ssafy.withssafy.src.dto.User
import org.w3c.dom.Comment

class ReplyAdapter (val context: Context) : RecyclerView.Adapter<ReplyAdapter.ViewHolder>(){
    private val TAG = "LocalCommentAdapter_ws"

    lateinit var commentList: MutableList<Comment>
//    lateinit var commentAllList : MutableList<Comment>
    lateinit var userList: MutableList<User>

    // 현재 로그인한 유저의 아이디
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    inner class ViewHolder(private val binding: ItemReplyListBinding) : RecyclerView.ViewHolder(binding.root) {

        val moreBtn = binding.replyItemIvMoreBtn

        fun bindInfo(comment: Comment) {

//            for (user in userList) {    // 작성자 nickname, profileImg 세팅
//                if(comment.userId == user.id) {
//                    binding.writer = user
//                }
//            }
//
//            moreBtn.isVisible = comment.userId == ApplicationClass.sharedPreferencesUtil.getUser().id
//
//            binding.comment = comment
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_reply_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]

        holder.apply {
            bindInfo(comment)
//            setIsRecyclable(false)

            // 작성자랑 작성자가 아닌 경우 팝업 메뉴 다르게 보여줘야 함.
            moreBtn.setOnClickListener {
//                val popup = PopupMenu(context, moreBtn)
//                MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)
//
//                popup.show()
//                popup.setOnMenuItemClickListener {
//                    when (it.itemId) {
//                        R.id.modify -> {
//                            modifyItemClickListener.onClick(comment.id, comment.boardId, position)
//                            return@setOnMenuItemClickListener true
//                        }
//                        R.id.delete -> {
//                            deleteItemClickListener.onClick(comment.id, comment.boardId, position)
//                            return@setOnMenuItemClickListener true
//                        }
//                        else -> {
//                            return@setOnMenuItemClickListener false
//                        }
//                    }
//                }
            }
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, commentId: Int)
    }

    private lateinit var addReplyClickListener : ItemClickListener

    fun setAddReplyItemClickListener(itemClickListener: ItemClickListener) {
        this.addReplyClickListener = itemClickListener
    }


    interface MenuClickListener {
        fun onClick(commentId: Int, postId: Int, position: Int)
    }

    private lateinit var modifyItemClickListener : MenuClickListener

    fun setModifyItemClickListener(menuClickListener: MenuClickListener) {
        this.modifyItemClickListener = menuClickListener
    }

    private lateinit var deleteItemClickListener : MenuClickListener

    fun setDeleteItemClickListener(menuClickListener: MenuClickListener) {
        this.deleteItemClickListener = menuClickListener
    }


}