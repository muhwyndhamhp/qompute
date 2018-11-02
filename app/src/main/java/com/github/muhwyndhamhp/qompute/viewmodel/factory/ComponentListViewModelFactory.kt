package com.github.muhwyndhamhp.qompute.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.viewmodel.ComponentListViewModel

class ComponentListViewModelFactory(val appRepository: AppRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComponentListViewModel(appRepository) as T
    }
}