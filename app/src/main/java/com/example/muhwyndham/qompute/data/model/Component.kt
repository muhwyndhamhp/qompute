package com.example.muhwyndham.qompute.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "components")
class Component (

    @PrimaryKey @SerializedName("id") @Expose
    val id: Int,

    @SerializedName("name") @Expose
    val name: String,

    @SerializedName("details") @Expose
    var details: String?,

    @SerializedName("brand") @Expose
    val brand: Int,

    @SerializedName("category") @Expose
    val category: Int,

    @SerializedName("subcategory") @Expose
    val subcategory: Int,

    @ColumnInfo(name = "brand_description") @SerializedName("brand_description") @Expose
    val brandDescription: String,

    @ColumnInfo(name = "category_description") @SerializedName("category_description") @Expose
    val categoryDescription: String,

    @ColumnInfo(name = "subcategory_description") @SerializedName("subcategory_description") @Expose
    val subcategoryDescription: String,

    @SerializedName("price") @Expose
    val price: Long,

    @SerializedName("weight") @Expose
    val weight: Double,

    @SerializedName("quantity") @Expose
    var quantity: Int?,

    @ColumnInfo(name = "stock_type")@SerializedName("stock_type") @Expose
    var stockType: Double?,

    @ColumnInfo(name = "link_toped")@SerializedName("link_toped") @Expose
    var linkToped: String?,

    @ColumnInfo(name = "link_shopee")@SerializedName("link_shopee") @Expose
    var linkShopee: String?,

    @ColumnInfo(name = "link_bukalapak")@SerializedName("link_bukalapak") @Expose
    var linkBukalapak: String?
):Serializable