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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentNotiListBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.home.FavoriteBoardAdapter


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
        notiListAdapter = NotiListAdapter()
        binding.notiListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = notiListAdapter
        }
        // 구분선 추가
        binding.notiListRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

}