package com.ssafy.withssafy.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.ssafy.withssafy.src.viewmodel.BoardViewModel
import com.ssafy.withssafy.src.viewmodel.HomeViewModel
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import com.ssafy.withssafy.src.viewmodel.UserViewModel

abstract class BaseFragment <B: ViewBinding> (
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
): Fragment(layoutResId){

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected val homeViewModel: HomeViewModel by activityViewModels()
    protected val userViewModel: UserViewModel by activityViewModels()
    protected val teamViewModel: TeamViewModel by activityViewModels()
    protected val boardViewModel: BoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}