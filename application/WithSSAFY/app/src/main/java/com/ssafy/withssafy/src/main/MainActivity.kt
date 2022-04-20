package com.ssafy.withssafy.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivityMainBinding
import com.ssafy.withssafy.src.main.board.BoardFragment
import com.ssafy.withssafy.src.main.home.HomeFragment
import com.ssafy.withssafy.src.main.notification.NotificationFragment
import com.ssafy.withssafy.src.main.schedule.ScheduleFragment
import com.ssafy.withssafy.src.main.team.TeamFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var bottomNavigation: BottomNavigationView

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

    private fun initNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivity_navHost) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainActivityBottomNav, navController)
    }
}
