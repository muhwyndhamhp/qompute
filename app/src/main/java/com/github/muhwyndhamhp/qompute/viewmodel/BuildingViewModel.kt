package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build

class BuildingViewModel(private val appRepository: AppRepository) : ViewModel() {

    val build: MutableLiveData<Build> = MutableLiveData()

    fun changeComponentCount(itemCount: Int, componentPosition: Int) {

    }
    init {
        val buildId = appRepository.insertBuild(
            Build(
                0,
                "",
                "",
                mutableListOf("","","","","","","","","","","","","","","","","","","",""),
                mutableListOf("","","","","","","","","","","","","","","","","","","",""),
                mutableListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
                0
            )
        )
        build.value = appRepository.getBuild(buildId)
    }

}