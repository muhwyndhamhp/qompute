package com.github.muhwyndhamhp.qompute.data.model

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BuildDao {
    @Query("SELECT * FROM builds")
    fun getAllBuilds(): LiveData<List<Build>>

    @Query("SELECT * FROM builds")
    fun getBuildsPaged(): DataSource.Factory<Int, Build>

    @Query("SELECT * FROM builds WHERE id = :id LIMIT 1")
    fun getSingleBuild(id: Long): LiveData<Build>

    @Query("SELECT * FROM builds WHERE name = :name LIMIT 1")
    fun getSingleBuild(name: String): LiveData<Build>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleBuild(build: Build): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buildList: List<Build>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateBuild(build: Build): Int

    @Delete
    fun deleteSingleBuild(build: Build)

    @Query("DELETE FROM builds")
    fun deleteAll()


}