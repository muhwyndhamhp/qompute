package com.example.muhwyndham.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muhwyndham.qompute.data.AppRepository
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.utils.ERROR_CODE_FAILED_TO_FETCH

class MainViewModel(val repository: AppRepository): ViewModel(){

    val componentList: MutableLiveData<List<Component>> = MutableLiveData()
    val exceptionList : MutableLiveData<MutableList<String>> = MutableLiveData()

    fun LoadAllComponents(){
        repository.reloadData1(object : AppRepository.LoadDataCallback{
            override fun onFailed(TAG: String, t: Throwable) {
                val exceptions: MutableList<String>? = exceptionList.value
                exceptions?.set(ERROR_CODE_FAILED_TO_FETCH, t.message!!)
                exceptionList.value = exceptions
            }

            override fun onSuccess(components: List<Component>) {
                componentList.value = components
            }

        })
    }

}