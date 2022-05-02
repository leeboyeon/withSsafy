package com.ssafy.withssafy.src.binding

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BindingAdapter
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.main.board.JobAdapter
import com.ssafy.withssafy.src.main.home.EmployInfoAdapter
import com.ssafy.withssafy.src.main.notification.MessageDetailAdapter
import com.ssafy.withssafy.src.main.notification.MessageGroupAdapter
import com.ssafy.withssafy.src.main.team.TeamAdapter
import com.ssafy.withssafy.util.CommonUtils
import java.text.SimpleDateFormat

@BindingAdapter("studyListData")
fun bindingStudyList(recyclerView: RecyclerView, data:List<Study>?){
    var adapter = recyclerView.adapter as TeamAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as TeamAdapter
    }
    adapter.list = data as MutableList<Study>
    adapter.notifyDataSetChanged()
}
@SuppressLint("SetTextI18n")
@BindingAdapter("studyLimitText")
fun bindStudyLimitText(textView: TextView, study:Study){
    if(study.studyMembers?.size == null){
        textView.text = "0/${study.sbLimit}"
    }
    textView.text = "${study.studyMembers?.size}/${study.sbLimit}"

}

@BindingAdapter("studyImageUrl")
fun bindImageUrl(imageView: ImageView, url:String?){
    Glide.with(imageView.context)
        .load("${ApplicationClass.IMGS_URL}${url}")
        .into(imageView)
}

@BindingAdapter("textViewPeople")
fun textViewConvertPeople(textView: TextView, size:Int){
    textView.text = "${size}명"
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("converMilliSecondToString")
fun textViewConvertDate(textView: TextView, time:String){
//    var formatter = SimpleDateFormat("yyyy-MM-dd HH:ss")
//    var date = formatter.format(time.toLong())
    textView.text = time.toString()
}


var isClicked = false
//데이터 바인딩에서 사용하기 위한 fun
@BindingAdapter("app:onThrottleFirstClick", "app:onThrottleInterval", requireAll = false)
fun onThrottleFirstClick(view: View, onClickListener: View.OnClickListener, isWithoutInterval: Boolean = false) {
    view.setOnClickListener { v ->
        if (isClicked.not()) {
            isClicked = true
            v?.run {
                if (isWithoutInterval) {
                    isClicked = false
                    onClickListener.onClick(v)
                } else {
                    postDelayed({
                        isClicked = false
                    }, 350L)
                    onClickListener.onClick(v)
                }
            }
        }
    }
}

@BindingAdapter("unixToDate")
fun bindBoardTime(textView: TextView, time: String) {
    textView.text = CommonUtils.unixTimeToDateFormatInBoard(time.toLong())
}

@BindingAdapter("decodingImg")
fun bindBitmapImg(imageView: ImageView, imgString: String) {
    Glide.with(imageView.context)
        .load(imgString)
        .into(imageView)
}

@BindingAdapter("ellipsisContent")
fun bindEllipsisContent(textView: TextView, content: String) {
    if(content.length > 45) {
        textView.text = "${content.substring(0, 40)} •••"
    } else {
        textView.text = content
    }
}

@BindingAdapter("messageTextView")
fun bindMsgTextView(textView: TextView, msg:Message){
    if(msg.u_fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
        textView.text = "익명${msg.u_toId}과의 대화"
    }else{
        textView.text = "익명${msg.u_fromId}과의 대화"
    }

}

@BindingAdapter("messageGroupDataList")
fun bindRecyclerViewMessageGroup(recyclerView: RecyclerView, data:List<Message>?){
    var adapter = recyclerView.adapter as MessageGroupAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as MessageGroupAdapter
    }
    adapter.list = data as MutableList<Message>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("messageTalkDataList")
fun bindRecyclerViewMessageTalk(recyclerView: RecyclerView, data:List<Message>?){
    var adapter = recyclerView.adapter as MessageDetailAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as MessageDetailAdapter
    }
    adapter.list = data as MutableList<Message>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("recruitListData")
fun bindingRecruitList(recyclerView: RecyclerView, data:List<Recruit>?){
    var adapter = recyclerView.adapter as JobAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as JobAdapter
    }
    adapter.list = data as MutableList<Recruit>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("recruitRecentListData")
fun bindingRecruitRecentList(recyclerView: RecyclerView, data:List<Recruit>?){
    var adapter = recyclerView.adapter as EmployInfoAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as EmployInfoAdapter
    }
    adapter.list = data as MutableList<Recruit>
    adapter.notifyDataSetChanged()
}
