package com.ssafy.withssafy.src.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivityMainBinding
import com.ssafy.withssafy.src.login.SingInActivity
import com.ssafy.withssafy.src.main.board.BoardFragment
import com.ssafy.withssafy.src.main.home.HomeFragment
import com.ssafy.withssafy.src.main.notification.NotificationFragment
import com.ssafy.withssafy.src.main.schedule.ScheduleFragment
import com.ssafy.withssafy.src.main.team.TeamFragment
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import kotlin.math.round

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val TAG = "MainActivity_ws"
    private lateinit var bottomNavigation: BottomNavigationView
    private val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val STORAGE_CODE = 99
    private val teamViewModel : TeamViewModel by viewModels()
    // 권한 허가
    var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() { // 권한 허가시 실행 할 내용
//            openGallery()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("Permission Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frame_layout, HomeFragment())
//            .commit()
//
//        bottomNavigation = binding.mainBottomNav
//        bottomNavigation.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.homeFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frame_layout, HomeFragment())
//                        .commit()
//                    true
//                }
//                R.id.scheduleFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frame_layout, ScheduleFragment())
//                        .commit()
//                    true
//                }
//                R.id.boardFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frame_layout, BoardFragment())
//                        .commit()
//                    true
//                }
//                R.id.notificationFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frame_layout, NotificationFragment())
//                        .commit()
//                    true
//                }
//                R.id.teamFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frame_layout, TeamFragment())
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
//        bottomNavigation.setOnItemReselectedListener { item ->
//            if(bottomNavigation.selectedItemId != item.itemId) {
//                bottomNavigation.selectedItemId = item.itemId
//            }
//        }
    }

    /**
     * bottom Navi 초기화
     */
    private fun initNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivity_navHost) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainActivityBottomNav, navController)
    }

    /**
     * BottomNavigation show/hide 설정
     * true - hide
     * false - show
     */
    fun hideBottomNavi(state : Boolean) {
        if(state) {
            binding.mainActivityBottomNav.visibility = View.GONE
        } else {
            binding.mainActivityBottomNav.visibility = View.VISIBLE
        }
    }

    /**
     * read gallery 권한 체크
     */
    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 26) { // 출처를 알 수 없는 앱 설정 화면 띄우기
            val pm: PackageManager = this@MainActivity.packageManager
            if (!pm.canRequestPackageInstalls()) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        Uri.parse("package:${context?.packageName}")
                    )
                )
            }
        }

        if (Build.VERSION.SDK_INT >= 23) { // 마시멜로(안드로이드 6.0) 이상 권한 체크
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다")
                .setDeniedMessage("If you reject permission,you can not use this service\n" +
                        "\n\nPlease turn on permissions at [Setting] > [Permission] ")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).check()
        }
//        else {
//            selectImg()
//        }
    }
    fun checkPermission(permissions: Array<out String>, type: Int): Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, type)
                    return false
                }
            }
        }
        return true
    }
    fun openGallery(code:Int) {
        if(checkPermission(STORAGE,STORAGE_CODE)){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            filterActivityLauncher.launch(intent)
            startActivityForResult(intent,code)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                STORAGE_CODE -> {
                    teamViewModel.uploadImageUri = data?.data
//                    mainViewModels.uploadedImageUri = data?.data
//
//                    Log.d(TAG, "onActivityResult: ${data?.data}")
//                    // 이미지 검사
//                    if(mainViewModels.uploadedImageUri == null) showCustomToast("이미지가 정상적으로 로드 되지 않았습니다.")
//                    else {
//                        mainViewModels.uploadedImage = MediaStore.Images.Media.getBitmap(contentResolver, mainViewModels.uploadedImageUri)
//                        checkTheType()
//                        photoDialog.dismiss()
//                    }
                }
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_CODE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        showCustomToast("저장소 권한을 승인해 주세요.")
                    }
                }
            }
        }
    }
    /**
     * 갤러리 사진 선택 result
     */
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
                val data = it.data
                Log.d(TAG, "${it.data}: ?/ ${it.data!!.clipData}")
                try {
//                    if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
//                        showCustomToast("이미지를 선택하지 않았습니다.")
//                    } else {
//                        if(data.clipData == null) {  // 이미지를 하나만 선택한 경우
//                            val imgUri = data
//
//                        }
//                    }
//
//                    currentImageUri?.let {
//                        if(Build.VERSION.SDK_INT < 28) {
//                            // 사진 set
//                            Glide.with(this)
//                                .load(currentImageUri)
//                                .into(binding.boardWriteIvSelectImg)
//
//                            // 파일 이름 set
//                            binding.boardWriteTvImgName.text = currentImageUri.lastPathSegment.toString()
//
//                            imgUri = currentImageUri
//                            fileExtension = requireActivity().contentResolver.getType(currentImageUri)
//                        } else {
//                            // 사진 set
//                            Glide.with(this)
//                                .load(currentImageUri)
//                                .into(binding.boardWriteIvSelectImg)
//
//                            // 파일 이름 set
//                            binding.boardWriteTvImgName.text = currentImageUri.lastPathSegment.toString()
//
//                            imgUri = currentImageUri
//                            fileExtension = requireActivity().contentResolver.getType(currentImageUri)
//                        }
//                    }
                }catch(e:Exception) {
                    e.printStackTrace()
                }
            } else if(it.resultCode == AppCompatActivity.RESULT_CANCELED){
                showCustomToast("사진 선택 취소")
            } else{
                Log.d(TAG,"filterActivityLauncher 실패")
            }
        }

    fun logout() {
        ApplicationClass.sharedPreferencesUtil.deleteUser()
        ApplicationClass.sharedPreferencesUtil.deleteUserCookie()
        ApplicationClass.sharedPreferencesUtil.deleteAutoLogin()

        val intent = Intent(this, SingInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
