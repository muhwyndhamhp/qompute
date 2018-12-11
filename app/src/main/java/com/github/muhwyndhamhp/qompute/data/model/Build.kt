package com.github.muhwyndhamhp.qompute.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.muhwyndhamhp.qompute.utils.ObjectTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "builds")
class Build(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") @Expose
    val id: Long,

    @SerializedName("name") @Expose
    var name: String,

    @SerializedName("description") @Expose
    val description: String,

    @TypeConverters(ObjectTypeConverter::class)
    @SerializedName("component_ids") @Expose
    var componentIds: MutableList<String>?,

    @TypeConverters(ObjectTypeConverter::class)
    @SerializedName("component_name") @Expose
    var componentName: MutableList<String>?,

    @TypeConverters(ObjectTypeConverter::class)
    @SerializedName("component_count") @Expose
    var componentCount: MutableList<Int>?,

    @TypeConverters(ObjectTypeConverter::class)
    @SerializedName("component_price") @Expose
    var componentPrice: MutableList<Long>?,


    @SerializedName("total_price") @Expose
    var totalPrice: Long?,

    @SerializedName("cpu_type") @Expose
    var cpuType: Int = 0,

    @SerializedName("socket_type") @Expose
    var socketType: Int = 0

) : Serializable