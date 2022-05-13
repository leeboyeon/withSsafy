package com.ssafy.withssafy.src.dto.study

import androidx.room.*

@Entity(tableName="team")
data class Build (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "minLimit") val minLimit : Int,
    @ColumnInfo(name = "maxLimit") val maxLimit : Int,
    @ColumnInfo(name = "classification") var classification : Int,
    @ColumnInfo(name = "options") var options : String
        )

@Dao
interface TeamDao {
    @Insert
    fun insertTeamType(team:Build)

    @Query("SELECT * FROM team ORDER BY id  DESC LIMIT 1")
    fun getTeamTypes() : Build
}