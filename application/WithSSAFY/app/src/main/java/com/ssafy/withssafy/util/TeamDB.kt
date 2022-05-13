package com.ssafy.withssafy.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ssafy.withssafy.src.dto.study.Build
import com.ssafy.withssafy.src.dto.study.TeamDao

@Database(entities = [Build::class], version = 1)
abstract class TeamDB : RoomDatabase(){
    abstract fun teamDao() : TeamDao

    companion object {
        private var INSTANCE : TeamDB? = null
        fun getInstance(context: Context) : TeamDB? {
            if(INSTANCE == null){
                synchronized(TeamDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, TeamDB::class.java, "team")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}