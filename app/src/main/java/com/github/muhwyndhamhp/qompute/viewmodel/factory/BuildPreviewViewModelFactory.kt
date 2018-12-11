package com.github.muhwyndhamhp.qompute.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.viewmodel.BuildPreviewViewModel

class BuildPreviewViewModelFactory(
    val appRepository: AppRepository,
    val build: Build
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BuildPreviewViewModel(appRepository, build) as T
    }
}