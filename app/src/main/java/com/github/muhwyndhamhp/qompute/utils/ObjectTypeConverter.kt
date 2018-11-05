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
            return when(data){
                null -> ComponentList.createComponentList(Collections.emptyList())
                else -> gson.fromJson<ComponentList>(data, object: TypeToken<ComponentList>(){}.type)
            }
        }

        @JvmStatic
        @TypeConverter
        fun componentListToString(componentList: ComponentList): String {
            return gson.toJson(componentList)
        }
    }
}