package com.github.muhwyndhamhp.qompute.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.muhwyndhamhp.qompute.utils.ObjectTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "builds")
class Build (

    @PrimaryKey
    @SerializedName("id") @Expose
    val id: String,

    @SerializedName("name") @Expose
    val name: String,

    @SerializedName("description") @Expose
    val description: String,

    @TypeConverters(ObjectTypeConverter::class)
    @SerializedName("component_list") @Expose
    var componentList: ComponentList?,

    @SerializedName("total_price") @Expose
    val totalPrice: Long

): Serializable