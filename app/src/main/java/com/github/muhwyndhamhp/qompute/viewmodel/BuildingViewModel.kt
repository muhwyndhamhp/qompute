package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build

class BuildingViewModel(private val appRepository: AppRepository) : ViewModel() {

    var build: MutableLiveData<Build> = MutableLiveData()
    val cpuBrand: MutableLiveData<Int> = MutableLiveData()
    val socketType: MutableLiveData<Int> = MutableLiveData()
    val componentPosition: MutableLiveData<Int> = MutableLiveData()

    fun changeComponentCount(itemCount: Int, componentPosition: Int) {
        val temp = build
        temp.value!!.componentCount!![componentPosition] = itemCount
        appRepository.updateBuild(temp.value!!)
    }

    fun initiateBuildObject(intExtra: Long) {
        if (intExtra == 0.toLong()) {
            build.value = Build(
                0,
                "",
                "",
                mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                mutableListOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                0
            )
        } else {
            build = appRepository.getBuild(intExtra) as MutableLiveData<Build>
        }
    }

    fun updateProcessorType() {
        clearAll(0)
        clearAll(1)
        clearAll(8)
    }

    private fun clearAll(i: Int) {
        val temp = build
        if (temp.value != null) {
            temp.value!!.componentCount!![i] = 1
            temp.value!!.componentIds!![i] = ""
            temp.value!!.componentName!![i] = ""
            appRepository.updateBuild(temp.value!!)
        }
    }

    fun deleteComponent(componentListPosition: Int) {
        clearAll(componentListPosition)
    }

    fun setComponentPosition(componentId: Int) {
        componentPosition.value = componentId
    }
}