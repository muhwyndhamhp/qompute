package com.github.muhwyndhamhp.qompute.data.model

import androidx.room.*

@Dao
interface ComponentDao {

    @Query("SELECT * FROM components")
    fun getAllComponents(): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc ORDER BY name ASC")
    fun getComponentsByCategoryAsc(catDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND subcategory_description != 'NAS' ORDER BY name ASC")
    fun getComponentsByCategoryAscHDD(catDesc: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND (brand_description LIKE :string OR name LIKE :string OR category_description LIKE :string OR subcategory_description LIKE :string) ORDER BY name ASC")
    fun getComponentsByCategorySearch(catDesc: String, string: String): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND (brand_description LIKE :string OR name LIKE :string OR category_description LIKE :string OR subcategory_description LIKE :string) AND CAST(price AS INTEGER) >= :minValString ORDER BY name ASC")
    fun getComponentsByCategorySearchFilteredMin(catDesc: String, string: String, minValString: Long): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND (brand_description LIKE :string OR name LIKE :string OR category_description LIKE :string OR subcategory_description LIKE :string) AND CAST(price AS INTEGER) <= :maxValString ORDER BY name ASC")
    fun getComponentsByCategorySearchFilteredMax(catDesc: String, string: String, maxValString: Long): List<Component>

    @Query("SELECT * FROM components WHERE category_description = :catDesc AND (brand_description LIKE :string OR name LIKE :string OR category_description LIKE :string OR subcategory_description LIKE :string) AND CAST(price AS INTEGER) >= :minValString AND CAST(price AS INTEGER) <= :maxValString ORDER BY name ASC")
    fun getComponentsByCategorySearchFilteredMinMax(catDesc: String, string: String, minValString: Long, maxValString: Long): List<Component>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleComponent(component: Component)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(componentList: List<Component>)

    @Delete
    fun deleteSingleComponent(component: Component)

    @Query("DELETE FROM components")
    fun deleteAll()
}

