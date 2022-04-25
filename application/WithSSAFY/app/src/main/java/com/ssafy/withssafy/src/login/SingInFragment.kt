package com.ssafy.withssafy.src.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentSingInBinding

class SingInFragment : BaseFragment<FragmentSingInBinding>(FragmentSingInBinding::bind,R.layout.fragment_sing_in) {
    private lateinit var signInActivity: SingInActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInActivity = context as SingInActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    private fun initListener() {
        forgotPwBtnClickEvent()
        signUpBtnClickEvent()
        loginBtnClickEvent()
    }

    /**
     * Forgot Password 버튼 클릭 이벤트
     */
    private fun forgotPwBtnClickEvent() {
        binding.signInFragmentLostPwTv.setOnClickListener {
            signInActivity.openFragment(1)
        }
    }

    /**
     * 회원가입 버튼 클릭 이벤트
     */
    private fun signUpBtnClickEvent() {
        binding.signInFragmentSignUpBtn.setOnClickListener {
            signInActivity.openFragment(3)
        }
    }

    /**
     * 로그인 버튼 클릭 이벤트
     */
    private fun loginBtnClickEvent() {
        binding.signInFragmentLoginBtn.setOnClickListener {
            signInActivity.openFragment(1)
        }
    }
}