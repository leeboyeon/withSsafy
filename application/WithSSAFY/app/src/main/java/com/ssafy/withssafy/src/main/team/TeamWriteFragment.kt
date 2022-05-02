package com.ssafy.withssafy.src.main.team

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.jakewharton.rxbinding3.widget.itemSelections
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamWriteBinding
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.dto.UserX
import com.ssafy.withssafy.src.dto.study.StudyMember
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

private const val TAG = "TeamWriteFragment"
class TeamWriteFragment : BaseFragment<FragmentTeamWriteBinding>(FragmentTeamWriteBinding::bind,R.layout.fragment_team_write) {
    private var category = ""
    private var area = ""
    private var outing = -1
    private var fileExtension : String? = ""
    private lateinit var mainActivity:MainActivity
    private val STORAGE_CODE = 99
    var people = 0

    private var studyId = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studyId = it.getInt("studyId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = teamViewModel
        setListener()
    }
    private fun setListener(){

        initButtons()
        initSpinner()
        initCheckBox()
        if(studyId > 0){
            initData()
        }
    }
    private fun initData(){
        runBlocking {
            teamViewModel.getStudy(studyId)
        }
        binding.fragmentTeamWriteSuccess.setText("수정")
        var study = teamViewModel.study.value!!
        binding.fragmentTeamWriteTitleEdit.setText(study.title.toString())
        binding.fragmentTeamWriteContentEdit.setText(study.content)
        var categorys = arrayListOf<String>("선택안함","어학","프로그래밍","면접","취업","CS","자율","기타")
        for(item in 0..categorys.size){
            if(categorys[item].contains(study.category)){
                binding.fragmentTeamWriteStudyType.setSelection(item)
                break
            }
        }
        
        binding.fragmentTeamWritePeople.setText(study.sbLimit.toString())
        teamViewModel.count = study.sbLimit
        teamViewModel.updateButtonText()
        
        var local = arrayListOf<String>("선택안함","서울","경기","인천","강원","제주","대전","충북","충남/세종","부산","울산","경남","대구","경북","광주","전남","전주/전북")
        for(item in 0..local.size){
            if(local[item].contains(study.area)){
                Log.d(TAG, "initData: ${item}")
                binding.fragmentTeamWriteLoc.setSelection(item)
                break
            }
        }
        if(study.isOuting == 0){
            binding.fragmentTeamWriteOfflineCheck.isChecked = false
            binding.fragmentTeamWriteOnlineCheck.isChecked = true
        }else if(study.isOuting == 1){
            binding.fragmentTeamWriteOfflineCheck.isChecked = true
            binding.fragmentTeamWriteOnlineCheck.isChecked = false
        }

        if(study.photoPath == null || study.photoPath.toString() == ""){
            binding.fragmentTeamWritePhoto.visibility = View.GONE
        }else{
            Glide.with(requireContext())
                .load("${ApplicationClass.IMGS_URL}${study.photoPath}")
                .into(binding.fragmentTeamWritePhoto)
        }

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
            if(studyId > 0){
                if(validateData()){
                    runBlocking { updateStudy() }
                }
            }else{
                if(validateData()){
                    runBlocking { insertStudy() }
                }
            }

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
    private fun validateData() : Boolean{
        var peopleChecked = false
        var contentChecked = false
        var LocChecked = false
        var typeChecked = false
        var optionChecked = false

        if(binding.fragmentTeamWritePeople.text.toString().toInt() > 0 ){
            peopleChecked = true
        }else{
            showCustomToast("모집인원이 부족합니다.")
            peopleChecked = false
        }

        if(binding.fragmentTeamWriteContentEdit.text.toString().length > 10){
            contentChecked = true
        }else{
            showCustomToast("최소 내용의 길이는 10 이상입니다.")
            contentChecked = false
        }

        if(binding.fragmentTeamWriteLoc.selectedItemPosition != 0){
            LocChecked = true
        }else{
            showCustomToast("지역을 선택해주세요")
            LocChecked = false
        }

        if(binding.fragmentTeamWriteStudyType.selectedItemPosition != 0){
            typeChecked = true
        }else{
            showCustomToast("분야를 선택해주세요")
            typeChecked = false
        }

        if(binding.fragmentTeamWriteOnlineCheck.isChecked == true || binding.fragmentTeamWriteOfflineCheck.isChecked == true){
            optionChecked = true
        }else if(binding.fragmentTeamWriteOnlineCheck.isChecked == true && binding.fragmentTeamWriteOfflineCheck.isChecked == true){
            showCustomToast("옵션을 하나만 선택해주세요")
            optionChecked = false
        }else{
            showCustomToast("옵션을 선택해주세요")
            optionChecked = false
        }

        if(optionChecked && typeChecked && LocChecked && peopleChecked && contentChecked){
            return true
        }else{
            return false
        }
    }
    private suspend fun updateStudy(){
        var local = area
        var category = category
        var content = binding.fragmentTeamWriteContentEdit.text.toString()
        var isOuting = outing
        var limit = binding.fragmentTeamWritePeople.text.toString()
        var title = binding.fragmentTeamWriteTitleEdit.text.toString()
        var today = System.currentTimeMillis()
        var study = Study(
            local,
            category,
            content,
            isOuting,
            limit.toInt(),
            title,
            today.toString(),
            ApplicationClass.sharedPreferencesUtil.getUser().id
        )
        if(teamViewModel.uploadImageUri == Uri.EMPTY || teamViewModel.uploadImageUri == null){
            runBlocking {
                val response = StudyService().updateStudy(studyId,study)
                if(response.code() == 204){
                    showCustomToast("수정되었습니다.")
                    this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                }
            }
        }else{
            val file = File(teamViewModel.uploadImageUri!!.path!!)
            var inputStream:InputStream? = null
            try{
                inputStream = mainActivity.contentResolver.openInputStream(teamViewModel.uploadImageUri!!)
            }catch (e : IOException){
                e.printStackTrace()
            }
            fileExtension = mainActivity.contentResolver.getType(teamViewModel.uploadImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),byteArrayOutputStream.toByteArray())
            val uploadFile = MultipartBody.Part.createFormData("file","${file.name}.${fileExtension?.substring(6)}",requestBody)
            runBlocking {
                val responseFile = StudyService().insertPhoto(uploadFile)
                if(responseFile.code() == 200){
                    if(responseFile.body() != null){
                        Log.d(TAG, "insertStudy: ${responseFile.body()}")
                        var study = Study(
                            local,
                            category,
                            content,
                            0,
                            isOuting,
                            limit.toInt(),
                            title,
                            today.toString(),
                            responseFile.body().toString(),
                            ApplicationClass.sharedPreferencesUtil.getUser().id
                        )

                        val response = StudyService().updateStudy(studyId,study)
                        if(response.code() == 204){
                            showCustomToast("수정되었습니다.")
                            this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                        }
                    }
                }
            }
        }
    }
    private suspend fun insertStudy(){
        var local = area
        var category = category
        var content = binding.fragmentTeamWriteContentEdit.text.toString()
        var isOuting = outing
        var limit = binding.fragmentTeamWritePeople.text.toString()
        val studyMember:List<StudyMember> = arrayListOf(StudyMember(ApplicationClass.sharedPreferencesUtil.getUser().id,ApplicationClass.sharedPreferencesUtil.getUser().name))
        var title = binding.fragmentTeamWriteTitleEdit.text.toString()
        var user = UserX (
            ApplicationClass.sharedPreferencesUtil.getUser().id,
        )
        var today = System.currentTimeMillis()
        var study = Study(
            local,
            category,
            content,
            isOuting,
            limit.toInt(),
            title,
            today.toString(),
            ApplicationClass.sharedPreferencesUtil.getUser().id
        )

        if(teamViewModel.uploadImageUri == Uri.EMPTY || teamViewModel.uploadImageUri == null){
            runBlocking {
                val response = StudyService().insertStudy(study)
                if(response.code() == 204){
                    showCustomToast("추가되었습니다.")
                    this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                }
            }
        }else{
            val file = File(teamViewModel.uploadImageUri!!.path!!)
            var inputStream:InputStream? = null
            try{
                inputStream = mainActivity.contentResolver.openInputStream(teamViewModel.uploadImageUri!!)
            }catch (e : IOException){
                e.printStackTrace()
            }
            fileExtension = mainActivity.contentResolver.getType(teamViewModel.uploadImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),byteArrayOutputStream.toByteArray())
            val uploadFile = MultipartBody.Part.createFormData("file","${file.name}.${fileExtension?.substring(6)}",requestBody)
            runBlocking {
                val responseFile = StudyService().insertPhoto(uploadFile)
                if(responseFile.code() == 200){
                    if(responseFile.body() != null){
                        Log.d(TAG, "insertStudy: ${responseFile.body()}")
                        var study = Study(
                            local,
                            category,
                            content,
                            0,
                            isOuting,
                            limit.toInt(),
                            title,
                            today.toString(),
                            responseFile.body().toString(),
                            ApplicationClass.sharedPreferencesUtil.getUser().id
                        )
                        Log.d(TAG, "insertStudy: $study")
                        val response = StudyService().insertStudy(study)
                        if(response.code() == 204){
                            showCustomToast("추가되었습니다.")
                            this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                        }
                    }
                }
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