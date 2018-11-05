package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1

class ComponentListViewModel(private val appRepository: AppRepository) : ViewModel() {

    val componentList = MutableLiveData<List<Component>>()
    val exceptionList = MutableLiveData<MutableList<Throwable>>()
    val tagList = MutableLiveData<MutableList<String>>()

    fun getData(catDesc: String) {
        appRepository.getComponentsByCategoryAsc(catDesc, object : AppRepository.LoadDataCallback {
            override fun onFailed(TAG: String, t: Throwable) {
                exceptionList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, t)
                tagList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, TAG)
            }

            override fun onSuccess(components: List<Component>) {
                componentList.value = components
            }
        })
    }

    fun getDataFromSearch(catDesc: String, queryString: String) {
        val string = "%$queryString%"
        componentList.value = appRepository.getComponentsByCategorySearch(catDesc, string)
    }

}