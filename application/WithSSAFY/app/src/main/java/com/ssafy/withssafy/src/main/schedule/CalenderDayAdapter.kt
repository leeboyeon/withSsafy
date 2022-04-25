package com.ssafy.withssafy.src.main.schedule

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import java.util.*
import kotlin.collections.ArrayList


class CalenderDayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>):
    RecyclerView.Adapter<CalenderDayAdapter.DayViewHolder>() {
    val ROW = 6

    inner class DayViewHolder(val layout: View):RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calender_day,parent,false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        var day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
        var clicked = false
        day.text = dayList[position].date.toString()
        day.setTextColor(when(position%7){
            0 -> Color.RED
            6-> Color.BLUE
            else -> Color.BLACK
        })

        if(tmpMonth != dayList[position].month){
            day.alpha = 0.4f
        }

    }
    override fun getItemCount(): Int {
        return ROW*7
    }
    interface ItemClickListener{
        fun onClick(view: View, position: Int, day: String, week:String)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}