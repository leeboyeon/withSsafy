package com.ssafy.withssafy.src.main.notification

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentNotiListBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.home.FavoriteBoardAdapter

// 임시로 해놓은 data class
data class Data (
    val id: Int,
    val title:String,
    val content:String,
    val period:String
) {
    constructor(title: String, content: String, period: String) : this(0, title, content, period)
}

class NotiListFragment : BaseFragment<FragmentNotiListBinding>(FragmentNotiListBinding::bind, R.layout.fragment_noti_list) {
    private lateinit var mainActivity: MainActivity

    lateinit var notiListAdapter: NotiListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initAdapter()
    }

    private fun initSpinner() {
        val spin = binding.notiListSp
        spin.apply {
            adapter = ArrayAdapter.createFromResource(requireContext(), R.array.noti, android.R.layout.simple_spinner_dropdown_item)
        }
        // spin.selectedItem.toString()
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initAdapter() {
        val data = arrayListOf<Data>()
        data.apply {
            add(Data(title = "현대백화점 그룹", content = "현대백화점 IT 개발 운영(서버, 안드로이드)", period = "2022년 4월 12일 ~ 2022년 5월 10일"))
            add(Data(title = "현대백화점 그룹", content = "현대백화점 IT 개발 운영(서버, 안드로이드)", period = "2022년 4월 12일 ~ 2022년 5월 10일"))
            add(Data(title = "현대백화점 그룹", content = "현대백화점 IT 개발 운영(서버, 안드로이드)", period = "2022년 4월 12일 ~ 2022년 5월 10일"))
            add(Data(title = "현대백화점 그룹", content = "현대백화점 IT 개발 운영(서버, 안드로이드)", period = "2022년 4월 12일 ~ 2022년 5월 10일"))
        }
        notiListAdapter = NotiListAdapter(data)
        binding.notiListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = notiListAdapter
        }
        // 구분선 추가
        binding.notiListRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // Recyclerview 스와이프해서 DELETE
        val notiListRvHelperCallback = NotiListRvHelperCallback(notiListAdapter).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }

        ItemTouchHelper(notiListRvHelperCallback).attachToRecyclerView(binding.notiListRv)

        binding.notiListRv.setOnTouchListener{ _, _ ->
            notiListRvHelperCallback.removePreviousClamp(binding.notiListRv)
            false
        }

    }

}