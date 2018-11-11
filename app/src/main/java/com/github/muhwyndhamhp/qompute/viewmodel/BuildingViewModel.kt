package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build

class BuildingViewModel(private val appRepository: AppRepository) : ViewModel() {

    lateinit var build: LiveData<Build>
    lateinit var cpuBrand: LiveData<Int>
    lateinit var socketType: LiveData<Int>

    fun changeComponentCount(itemCount: Int, componentPosition: Int) {
//        if (build.value!!.componentIds!![componentPosition] != "") {
            val temp = build
            temp.value!!.componentCount!![componentPosition] = itemCount
            appRepository.updateBuild(temp.value!!)
//        }
    }

    fun initiateBuildObject(intExtra: Long) {
        build = if (intExtra == 0.toLong()) {
            val buildId = appRepository.insertBuild(
                Build(
                    0,
                    "",
                    "",
                    mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                    mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                    mutableListOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                    0
                )
            )
            appRepository.getBuild(buildId)
        } else
            appRepository.getBuild(intExtra)
    }

    fun updateProcessorType() {
        clearAll(0)
        clearAll(1)
        clearAll(8)
    }

    private fun clearAll(i: Int) {
        val temp = build
        temp.value!!.componentCount!![i] = 0
        temp.value!!.componentIds!![i] = ""
        temp.value!!.componentName!![i] = ""
        appRepository.updateBuild(temp.value!!)
    }

    fun deleteComponent(componentListPosition: Int) {
        clearAll(componentListPosition)
    }
}