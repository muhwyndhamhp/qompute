package com.github.muhwyndhamhp.qompute.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "components")
class Component (

    @PrimaryKey @SerializedName("id") @Expose
    val id: String,

    @SerializedName("name") @Expose
    val name: String,

    @SerializedName("details") @Expose
    var details: String?,

    @SerializedName("brand") @Expose
    val brand: String,

    @SerializedName("category") @Expose
    val category: String,

    @SerializedName("subcategory") @Expose
    val subcategory: String,

    @ColumnInfo(name = "brand_description") @SerializedName("brand_description") @Expose
    val brandDescription: String,

    @ColumnInfo(name = "category_description") @SerializedName("category_description") @Expose
    val categoryDescription: String,

    @ColumnInfo(name = "subcategory_description") @SerializedName("subcategory_description") @Expose
    val subcategoryDescription: String,

    @SerializedName("price") @Expose
    val price: String,

    @SerializedName("weight") @Expose
    val weight: String,

    @SerializedName("quantity") @Expose
    var quantity: String?,

    @ColumnInfo(name = "stock_type")@SerializedName("stock_type") @Expose
    var stockType: String?,

    @ColumnInfo(name = "link_toped")@SerializedName("link_toped") @Expose
    var linkToped: String?,

    @ColumnInfo(name = "link_shopee")@SerializedName("link_shopee") @Expose
    var linkShopee: String?,

    @ColumnInfo(name = "link_bukalapak")@SerializedName("link_bukalapak") @Expose
    var linkBukalapak: String?,

    @ColumnInfo(name = "last_update")@SerializedName("last_update")
    var lastUpdate: Long?
):Serializable