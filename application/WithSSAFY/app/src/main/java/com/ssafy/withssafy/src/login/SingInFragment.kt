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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInActivity = context as SingInActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInFragmentLoginBtn.setOnClickListener {
            signInActivity.openFragment(1)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}