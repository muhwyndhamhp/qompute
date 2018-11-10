package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build

class BuildingViewModel(private val appRepository: AppRepository) : ViewModel() {

    val build: MutableLiveData<Build> = MutableLiveData()

    fun changeComponentCount(itemCount: Int, componentPosition: Int) {
        if (build.value!!.componentIds!![componentPosition] != "") {
            build.value!!.componentCount!![componentPosition] = itemCount
        }
    }

    fun initiateBuildObject(intExtra: Long) {
        if (intExtra == 0.toLong()) {
            val buildId = appRepository.insertBuild(
                Build(
                    0,
                    "",
                    "",
                    mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                    mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    0
                )
            )
            build.value = appRepository.getBuild(buildId)
        } else
            build.value = appRepository.getBuild(intExtra)
    }

    fun updateProcessorType() {
        clearAll(0)
        clearAll(1)
        clearAll(8)
    }

    private fun clearAll(i: Int) {
        build.value!!.componentCount!![i] = 0
        build.value!!.componentIds!![i] = ""
        build.value!!.componentName!![i] = ""
    }

    fun deleteComponent(componentListPosition: Int) {
        clearAll(componentListPosition)
    }

    fun updateDatabase() {
        appRepository.updateBuild(build.value!!)
    }
}