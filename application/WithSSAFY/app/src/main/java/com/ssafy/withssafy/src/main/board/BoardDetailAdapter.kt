package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemPostListBinding
import com.ssafy.withssafy.src.dto.User
import java.util.*

class BoardDetailAdapter (val context: Context) : RecyclerView.Adapter<BoardDetailAdapter.ViewHolder>(){
//class LocalBoardAdapter(val context: Context) : ListAdapter<Board, LocalBoardAdapter.LocalBoardViewHolder>(DiffCallback) {
    lateinit var postList : MutableList<Int>    // !dto 수정
    lateinit var userList: MutableList<User>
    lateinit var userLikePost: MutableList<Int>

    inner class ViewHolder(private val binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {
        val heartBtn = binding.postListItemLottieHeart
        val commentBtn = binding.postListItemLlComment
        val moreBtn = binding.postListItemBtnMore

        fun bindInfo() {


//            for (i in userLikePost) {   // 로그인 유저가 좋아요 누른 게시글 표시
//                if(post.id == i) {
//                    heartBtn.progress = 0.5F
//                    break
//                }
//                heartBtn.progress = 0.0F
//            }
//
//            moreBtn.isVisible = post.userId == ApplicationClass.sharedPreferencesUtil.getUser().id
            binding.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val post = getItem(position)
        val post = postList[position]
        holder.apply {
//            bindInfo()
//            setIsRecyclable(false)
//            heartBtn.setOnClickListener {
//                heartItemClickListener.onClick(it as LottieAnimationView, position, post.id)
//            }
//
//            commentBtn.setOnClickListener {
//                commentItemClickListener.onClick(it, post.id)
//            }
//
//            moreBtn.setOnClickListener {
//                val popup = PopupMenu(context, moreBtn)
//                if(post.userId == ApplicationClass.sharedPreferencesUtil.getUser().id) {    // 작성자는 수정, 삭제 팝업 메뉴
//                    MenuInflater(context).inflate(R.menu.popup_menu_writer, popup.menu)
//
//                    popup.show()
//                    popup.setOnMenuItemClickListener {
//                        when(it.itemId) {
//                            R.id.modify -> {
//                                modifyItemClickListener.onClick(post.id, position)
//                                return@setOnMenuItemClickListener true
//                            }
//                            R.id.delete -> {
//                                deleteItemClickListener.onClick(post.id, position)
//                                return@setOnMenuItemClickListener true
//                            } else -> {
//                                return@setOnMenuItemClickListener false
//                            }
//                        }
//                    }
//                } else {    // 작성자가 아닌 경우 쪽지 보내기, 신고 팝업 메뉴
//                    MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)
//
//                    popup.show()
//                    popup.setOnMenuItemClickListener {
//                        when(it.itemId) {
//                            R.id.sendNote -> {
//                                modifyItemClickListener.onClick(post.id, position)
//                                return@setOnMenuItemClickListener true
//                            }
//                            R.id.report -> {
//                                deleteItemClickListener.onClick(post.id, position)
//                                return@setOnMenuItemClickListener true
//                            } else -> {
//                            return@setOnMenuItemClickListener false
//                        }
//                        }
//                    }
//                }
//            }
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    interface HeartItemClickListener {
        fun onClick(heart: LottieAnimationView, position: Int, id: Int)
    }


    private lateinit var heartItemClickListener : HeartItemClickListener

    fun setHeartItemClickListener(itemClickListener: HeartItemClickListener) {
        this.heartItemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onClick(view: View, postId: Int)
    }

    private lateinit var commentItemClickListener : ItemClickListener

    fun setCommentItemClickListener(itemClickListener: ItemClickListener) {
        this.commentItemClickListener = itemClickListener
    }

    interface MenuClickListener {
        fun onClick(postId: Int, position: Int)
    }

    private lateinit var modifyItemClickListener : MenuClickListener

    fun setModifyItemClickListener(menuClickListener: MenuClickListener) {
        this.modifyItemClickListener = menuClickListener
    }

    private lateinit var deleteItemClickListener : MenuClickListener

    fun setDeleteItemClickListener(menuClickListener: MenuClickListener) {
        this.deleteItemClickListener = menuClickListener
    }

//    object DiffCallback : DiffUtil.ItemCallback<Board>() {
//        override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }



}