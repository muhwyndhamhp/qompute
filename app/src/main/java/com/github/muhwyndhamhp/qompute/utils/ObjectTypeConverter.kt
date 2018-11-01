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
        fun stringToObject(data: String): ComponentList {
            when(data){
                null -> return ComponentList.createComponentList(Collections.emptyList())
                else -> return gson.fromJson<ComponentList>(data, object: TypeToken<ComponentList>(){}.type)
            }
        }

        @JvmStatic
        @TypeConverter
        fun objectToString(componentList: ComponentList): String {
            return gson.toJson(componentList)
        }
    }
}