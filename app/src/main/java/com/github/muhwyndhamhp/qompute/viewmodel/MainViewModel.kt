package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val fragmentPosition = MutableLiveData<Int>()

    fun setFragmentPosition(position: Int) {
        fragmentPosition.value = position
    }

}