package com.ssafy.withssafy.src.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentSignUpBinding
import kotlinx.coroutines.runBlocking

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::bind, R.layout.fragment_sign_up) {
    private lateinit var signInActivity: SingInActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInActivity = context as SingInActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getClassRoomListInit()
        initSpinner()
    }

    private fun initListener() {

    }

    private fun getClassRoomListInit() {
        runBlocking {
            userViewModel.getClassRoomList()
        }
    }

    /**
     * 기수, 반 spinner 초기화
     */
    private fun initSpinner() {
        var genList = arrayListOf("기수")
        var areaList = arrayListOf("지역")
        var classList = arrayListOf("반")

        val classRoomList = userViewModel.classRommList.value
        for(i in classRoomList!!) {
            genList.add(i.generation)
            areaList.add(i.area)
            classList.add(i.classDescription)
        }
        // 중복 제거
        val newGetList = genList.toSet()
        val newAreaList = areaList.toSet()
        val newClassList = classList.toSet()

        val genSpin = binding.signUpFragmentSpinnerStuGen
        val areaSpin = binding.signUpFragmentSpinnerArea
        val classSpin = binding.signUpFragmentSpinnerClass

        genSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newGetList.toList())
        }
        areaSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newAreaList.toList())
        }
        classSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newClassList.toList())
        }
    }
}