package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentNoticeWriteBinding
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.NoticeService
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

private const val TAG = "NoticeWriteFragment"
class NoticeWriteFragment : BaseFragment<FragmentNoticeWriteBinding>(FragmentNoticeWriteBinding::bind, R.layout.fragment_notice_write) {
    private var type = 0
    private var gen = ""
    private var area = ""
    private var classNum = ""
    private val NOTICE_CODE = 100
    private var classRoomId = 0
    private var fileExtension : String? = ""
    private lateinit var mainActivity:MainActivity
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getClassRoomListInit()
        initSpinner()
        selectSpinner()
        initButton()
    }

    private fun getClassRoomListInit() {
        runBlocking {
            userViewModel.getClassRoomList()
        }
    }

    private fun initSpinner() {
        var typeList = arrayListOf("선택", "학습", "평가", "운영", "사이트", "기타")
        var genList = arrayListOf("선택", "전체")
        var areaList = arrayListOf("선택", "전체", "서울", "대전", "광주", "구미", "부울경")
        var classList = arrayListOf("선택", "전체")

        val classRoomList = userViewModel.classRommList.value
        for(i in classRoomList!!) {
            genList.add(i.generation)
            classList.add(i.classDescription)
        }
        val newGenList = genList.toSet()
        val newClassList = classList.toSet()

        val typeSpin = binding.fragmentNoticeWriteTypeSpin
        val genSpin = binding.fragmentNoticeWriteGenSpin
        val areaSpin = binding.fragmentNoticeWriteAreaSpin
        val classSpin = binding.fragmentNoticeWriteClassSpin

        typeSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, typeList)
        }
        genSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newGenList.toList())
        }
        areaSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, areaList)
        }
        classSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newClassList.toList())
        }
    }

    private fun initButton() {
        binding.fragmentNoticeWriteCamera.setOnClickListener {
            mainActivity.openGallery(NOTICE_CODE)
            noticeViewModel.uploadImageUri.observe(viewLifecycleOwner) {
                if(it!=null){
                    binding.fragmentNoticeWritePhotoGroup.visibility = View.VISIBLE
                    Glide.with(requireContext())
                        .load(noticeViewModel.uploadImageUri.value)
                        .into(binding.fragmentNoticeWritePhoto)
                }
            }

        }
        binding.fragmentNoticeWritePhotoDelete.setOnClickListener {
            noticeViewModel.setUploadImageUri(null)
            //noticeViewModel.uploadImageUri = null
            binding.fragmentNoticeWritePhotoGroup.visibility = View.GONE
        }
        binding.fragmentNoticeWriteSuccess.setOnClickListener {
            insertNotice()
        }
    }

    private fun insertNotice() {
        val title = binding.fragmentNoticeWriteTitleEdit.text.toString()
        val content = binding.fragmentNoticeWriteContentEdit.text.toString()
        if(title != "" && content != "" && type == 0 && gen != "" && area != "" && classNum != "") {
            if (noticeViewModel.uploadImageUri == Uri.EMPTY || noticeViewModel.uploadImageUri == null) {
                var notice = Notice(classRoomId, content, title, type, userId)
                runBlocking {
                    val response = NoticeService().insertNotice(notice)
                    if (response.code() == 204) {
                        showCustomToast("공지사항이 등록되었습니다.")
                        this@NoticeWriteFragment.findNavController().popBackStack()
                    } else {
                        showCustomToast("공지사항 등록에 실패했습니다.")
                    }
                }
            } else {
                val file = File(noticeViewModel.uploadImageUri!!.value!!.path!!)
                var inputStream: InputStream? = null
                try {
                    inputStream =
                        mainActivity.contentResolver.openInputStream(noticeViewModel.uploadImageUri!!.value!!)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                fileExtension = mainActivity.contentResolver.getType(noticeViewModel.uploadImageUri!!.value!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                val requestBody = RequestBody.create(MediaType.parse("image/*"), byteArrayOutputStream.toByteArray())
                val uploadFile = MultipartBody.Part.createFormData("file", "${file.name}.${fileExtension?.substring(6)}", requestBody)
                runBlocking {
                    val responseFile = NoticeService().insertNoticePhoto(uploadFile)
                    if (responseFile.code() == 200) {
                        if (responseFile.body() != null) {
                            Log.d(TAG, "insertNotice: ${responseFile.body()}")
                            var notice = Notice(classRoomId, content, responseFile.body().toString(), title, type, userId)
                            Log.d(TAG, "insertNotice: $notice")
                            val response = NoticeService().insertNotice(notice)
                            if (response.code() == 204) {
                                showCustomToast("공지사항이 등록되었습니다.")
                                this@NoticeWriteFragment.findNavController().popBackStack()
                            } else {
                                showCustomToast("공지사항 등록에 실패했습니다.")
                            }
                        }
                    }
                }
            }
        } else {
            showCustomToast("항목을 모두 입력해주세요.")
        }
    }

    private fun selectSpinner() {
        val typeSpin = binding.fragmentNoticeWriteTypeSpin
        val genSpin = binding.fragmentNoticeWriteGenSpin
        val areaSpin = binding.fragmentNoticeWriteAreaSpin
        val classSpin = binding.fragmentNoticeWriteClassSpin

        typeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        genSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        gen = ""
                    }
                    else -> {
                        gen = genSpin.selectedItem.toString()

                        if(gen != "" && area != "" && classNum != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == classNum) {
                                    classRoomId = i.id
                                }
                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        areaSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        area = ""
                    }
                    else -> {
                        area = areaSpin.selectedItem.toString()

                        // 기수와 지역을 선택하면 반은 해당하는 정보에 맞는 리스트로 다시 초기화해준다.
                        if(gen != "" && area != "") {
                            initClassSpinner()
                        }
                        if(gen != "" && area != "" && classNum != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == classNum) {
                                    classRoomId = i.id
                                }
                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        classSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        classNum = ""
                    }
                    else -> {
                        classNum = classSpin.selectedItem.toString()

                        if(gen != "" && area != "" && classNum != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == classNum) {
                                    classRoomId = i.id
                                }
                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun initClassSpinner() {
        var classList = arrayListOf("선택", "전체")

        val classRoomList = userViewModel.classRommList.value

        for(i in classRoomList!!) {
            if(i.generation == gen && i.area == area) {
                classList.add(i.classDescription)
            }
        }
        // 중복 제거
        val newClassList = classList.toSet()

        val classSpin = binding.fragmentNoticeWriteClassSpin

        classSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newClassList.toList())
        }
    }


}