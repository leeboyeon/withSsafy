package com.ssafy.withssafy.src.main.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentUserInfoChangeBinding
import com.ssafy.withssafy.src.main.MainActivity


class UserInfoChangeFragment : BaseFragment<FragmentUserInfoChangeBinding>(FragmentUserInfoChangeBinding::bind, R.layout.fragment_user_info_change) {
    private lateinit var mainActivity: MainActivity
    private var changeType: Int = -1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            changeType = getInt("changeType")
        }
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()

        binding.pwChangeBack.setOnClickListener{
            this@UserInfoChangeFragment.findNavController().popBackStack()
        }

        binding.classChangeBack.setOnClickListener{
            this@UserInfoChangeFragment.findNavController().popBackStack()
        }
    }

    private fun initLayout() {
        if(changeType == 0) {
            binding.layoutPwChange.visibility = View.VISIBLE
            binding.layoutClassChange.visibility = View.GONE
        } else if(changeType == 1) {
            binding.layoutPwChange.visibility = View.GONE
            binding.layoutClassChange.visibility = View.VISIBLE
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserInfoChangeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}