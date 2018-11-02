package com.github.muhwyndhamhp.qompute.data.model

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface ComponentDao {

    @Query("SELECT * FROM components")
    fun getAllComponents(): List<Component>

    @Query("SELECT * FROM components")
    fun getComponentsPaged(): DataSource.Factory<Int, Component>

    @Query("SELECT * FROM components WHERE id = :id LIMIT 1")
    fun getSingleComponent(id: Int): Component

    @Query("SELECT * FROM components WHERE name = :name LIMIT 1")
    fun getSingleComponent(name: String): Component

    @Query("SELECT * FROM components WHERE brand_description = :brandDesc")
    fun getComponentsByBrandDesc(brandDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc")
    fun getComponentsByCategory(catDesc: String): List<Component>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleComponent(component: Component)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(componentList: List<Component>)

    @Delete
    fun deleteSingleComponent(component: Component)

    @Query("DELETE FROM components")
    fun deleteAll()
}

