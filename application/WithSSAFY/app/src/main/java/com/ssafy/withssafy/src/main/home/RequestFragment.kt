package com.ssafy.withssafy.src.main.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentRequestBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.service.RecruitService
import com.ssafy.withssafy.src.network.service.UserService
import kotlinx.coroutines.runBlocking
import retrofit2.Response


class RequestFragment : BaseFragment<FragmentRequestBinding>(FragmentRequestBinding::bind, R.layout.fragment_request) {
    lateinit var requestAdapter: RequestAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userViewModel = userViewModel
        runBlocking {
            userViewModel.getStateZeroUserList()
        }
        initButton()
        initAdapter()
    }

    private fun initButton() {
        binding.fragmentRequestAppBarPrev.setOnClickListener{
            this@RequestFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        requestAdapter = RequestAdapter(false)
        userViewModel.stateZeroUserList.observe(viewLifecycleOwner) {
            requestAdapter.list = it
        }
        binding.fragmentRequestRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = requestAdapter
        }
        binding.fragmentRequestRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        requestAdapter.setItemClickListener(object : RequestAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                showDialog(id)
            }
        })
    }

    private fun showDialog(id: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("")
            .setMessage("승인하시겠습니까?")
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    updateState(id)
                }
            })
            .setNegativeButton("취소", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {

                }
            })
            .create()
            .show()
    }

    private fun updateState(id: Int) {
        var response : Response<User>
        runBlocking {
            response = UserService().updateState(id, 1)
        }
            if (response.code() == 200) {
                showCustomToast("승인이 완료되었습니다.")
                runBlocking {
                    userViewModel.getStateZeroUserList()
                }
                requestAdapter.notifyDataSetChanged()
            } else {
                showCustomToast("승인을 실패했습니다.")
            }

    }


}