package com.ssafy.withssafy.src.binding

import android.view.View
import androidx.databinding.BindingAdapter
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.withssafy.src.dto.study.Study
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
@BindingAdapter("studyLimitText")
fun bindStudyLimitText(textView: TextView, limit:Int, curUser:Int){
    textView.text = "${curUser}/$limit"
}

@BindingAdapter("studyImageUrl")
fun bindImageUrl(imageView: ImageView, url:String?){
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

@BindingAdapter("textViewPeople")
fun textViewConvertPeople(textView: TextView, size:Int){
    textView.text = "${size}명"
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("converMilliSecondToString")
fun textViewConvertDate(textView: TextView, time:String){
    var formatter = SimpleDateFormat("yyyy-MM-dd HH:ss")
    var date = formatter.format(time.toLong())
    textView.text = date.toString()
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
        .asBitmap()
        .load(CommonUtils.base64ToImg(imgString))
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