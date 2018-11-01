package com.github.muhwyndhamhp.qompute.data.model

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BuildDao{
    @Query("SELECT * FROM builds")
    fun getAllBuilds(): List<Build>

    @Query("SELECT * FROM builds")
    fun getBuildsPaged(): DataSource.Factory<Int, Build>

    @Query("SELECT * FROM builds WHERE id = :id LIMIT 1")
    fun getSingleBuild(id: Int): Build

    @Query("SELECT * FROM builds WHERE name = :name LIMIT 1")
    fun getSingleBuild(name: String): Build

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleBuild(build: Build)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buildList: List<Build>)

    @Delete
    fun deleteSingleBuild(build: Build)

    @Query("DELETE FROM builds")
    fun deleteAll()
}