package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build

class BuildPreviewViewModel(
    val appRepository: AppRepository,
    val build: Build
): ViewModel(){


    val buildData: MutableLiveData<Build> = MutableLiveData()
    init {
        buildData.value = build
    }
}