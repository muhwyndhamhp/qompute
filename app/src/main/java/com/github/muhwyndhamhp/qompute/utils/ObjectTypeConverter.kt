package com.github.muhwyndhamhp.qompute.utils

import androidx.room.TypeConverter
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.data.model.ComponentList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ObjectTypeConverter {

    companion object {
        val gson = Gson()

        @JvmStatic
        @TypeConverter
        fun stringToComponentList(data: String): ComponentList {
            return gson.fromJson<ComponentList>(data, object: TypeToken<ComponentList>(){}.type)
        }

        @JvmStatic
        @TypeConverter
        fun componentListToString(componentList: ComponentList): String {
            return gson.toJson(componentList)
        }

        @JvmStatic
        @TypeConverter
        fun arrayListToString(arrayList: List<String>): String {
            return gson.toJson(arrayList)
        }

        @JvmStatic
        @TypeConverter
        fun stringToArrayList(data: String): List<String> {
            return gson.fromJson<List<String>>(data, object:TypeToken<List<String>>(){}.type)
        }

        @JvmStatic
        @TypeConverter
        fun intArrayToString(intArray: List<Int>): String {
            return gson.toJson(intArray)
        }

        @JvmStatic
        @TypeConverter
        fun stringToIntArray(data: String): List<Int> {
            return gson.fromJson<List<Int>>(data, object:TypeToken<List<Int>>(){}.type)
        }

        @JvmStatic
        @TypeConverter
        fun longArrayToString(intArray: List<Long>): String {
            return gson.toJson(intArray)
        }

        @JvmStatic
        @TypeConverter
        fun stringToLongArray(data: String): List<Long> {
            return gson.fromJson<List<Long>>(data, object:TypeToken<List<Long>>(){}.type)
        }
    }
}