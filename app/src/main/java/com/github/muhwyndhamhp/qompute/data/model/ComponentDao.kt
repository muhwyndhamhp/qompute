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

    @Query("SELECT * FROM components WHERE brand_description = :brandDesc ORDER BY name ASC")
    fun getComponentsByBrandDescriptionAsc(brandDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc ORDER BY name ASC")
    fun getComponentsByCategoryAsc(catDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND subcategory_description != 'NAS' ORDER BY name ASC")
    fun getComponentsByCategoryAscHDD(catDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE brand_description = :brandDesc ORDER BY name DESC")
    fun getComponentsByBrandDescriptionDesc(brandDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc ORDER BY name DESC")
    fun getComponentsByCategoryDesc(catDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND (brand_description LIKE :string OR name LIKE :string OR category_description LIKE :string OR subcategory_description LIKE :string) ORDER BY name ASC")
    fun getComponentsByCategorySearch(catDesc: String, string: String): List<Component>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleComponent(component: Component)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(componentList: List<Component>)

    @Delete
    fun deleteSingleComponent(component: Component)

    @Query("DELETE FROM components")
    fun deleteAll()
}

