package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository

class MainViewModel(val repository: AppRepository) : ViewModel() {

    val fragmentPosition = MutableLiveData<Int>()

    fun setFragmentPosition(position: Int) {
        fragmentPosition.value = position
    }

}