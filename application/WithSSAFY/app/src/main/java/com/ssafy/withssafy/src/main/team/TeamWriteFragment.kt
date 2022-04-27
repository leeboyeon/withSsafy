package com.ssafy.withssafy.src.main.team

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamWriteBinding
import com.ssafy.withssafy.src.dto.Study
import com.ssafy.withssafy.src.dto.UserX
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.StudyService
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TeamWriteFragment : BaseFragment<FragmentTeamWriteBinding>(FragmentTeamWriteBinding::bind,R.layout.fragment_team_write) {
    private var category = ""
    private var area = ""
    private var outing = -1

    private lateinit var mainActivity:MainActivity
    private val STORAGE_CODE = 99

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initButtons()
        initSpinner()
        initCheckBox()
    }
    private fun initCheckBox(){
        binding.fragmentTeamWriteOnlineCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.fragmentTeamWriteOfflineCheck.isChecked = false
                 outing = 0
            }else{
                binding.fragmentTeamWriteOfflineCheck.isChecked = true
                outing = 1
            }
        }
        binding.fragmentTeamWriteOfflineCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.fragmentTeamWriteOnlineCheck.isChecked = false
                outing = 1
            }else{
                binding.fragmentTeamWriteOnlineCheck.isChecked =false
                outing = 0
            }
        }
    }
    private fun initSpinner(){
        var categorys = arrayListOf<String>("선택안함","어학","프로그래밍","면접","취업","CS","자율","기타")
        binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorys)
        binding.fragmentTeamWriteStudyType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = binding.fragmentTeamWriteStudyType.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        var local = arrayListOf<String>("선택안함","서울","경기","인천","강원","제주","대전","충북","충남/세종","부산","울산","경남","대구","경북","광주","전남","전주/전북")
        binding.fragmentTeamWriteLoc.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, local)
        binding.fragmentTeamWriteLoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                area = binding.fragmentTeamWriteLoc.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
    private fun initButtons(){
        binding.fragmentTeamWriteAppBarPrev.setOnClickListener {
            this@TeamWriteFragment.findNavController().popBackStack()
        }
        binding.fragmentTeamWriteSuccess.setOnClickListener {
            runBlocking { insertStudy() }
        }
        binding.fragmentTeamWriteCamera.setOnClickListener {
            mainActivity.openGallery(STORAGE_CODE)
            if(teamViewModel.uploadImageUri!=null){
                binding.fragmentTeamWritePhotoGroup.visibility = View.VISIBLE
                Glide.with(requireContext())
                    .load(teamViewModel.uploadImageUri)
                    .into(binding.fragmentTeamWritePhoto)
            }
        }
    }
    private suspend fun insertStudy(){
        var title = binding.fragmentTeamWriteTitleEdit.text.toString()
        var content = binding.fragmentTeamWriteContentEdit.text.toString()
        var limit = binding.fragmentTeamWritePeople.text.toString()
        var category = category
        var local = area
        var today = System.currentTimeMillis()
        var user = UserX (
            ApplicationClass.sharedPreferencesUtil.getUser().id,
                )
        var study = Study(
            local,
            category,
            content,
            0,
            outing,
            limit.toInt(),
            title,
            user,
            today.toString(),
            ""
        )
        runBlocking {
            val response = StudyService().insertStudy(study)
            if(response.code() == 204){
                showCustomToast("추가되었습니다.")
                this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamWriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}