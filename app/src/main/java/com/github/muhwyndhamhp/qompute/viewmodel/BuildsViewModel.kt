package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build

class BuildsViewModel(val appRepository: AppRepository): ViewModel(){

    var builds : LiveData<List<Build>> = MutableLiveData()

    init {
        builds= appRepository.getAllBuilds()
    }
}