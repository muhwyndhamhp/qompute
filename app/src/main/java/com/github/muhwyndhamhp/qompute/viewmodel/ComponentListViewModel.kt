package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1

class ComponentListViewModel(private val appRepository: AppRepository) : ViewModel() {

    val componentListA = MutableLiveData<List<Component>>()

    val exceptionList = MutableLiveData<MutableList<Throwable>>()
    val tagList = MutableLiveData<MutableList<String>>()


    fun getData(catDesc: String) {
        appRepository.getComponentsByCategoryAsc(catDesc, object : AppRepository.LoadDataCallback {
            override fun onFailed(TAG: String, t: Throwable) {
                exceptionList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, t)
                tagList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, TAG)
            }

            override fun onSuccess(components: List<Component>) {
                componentListA.value = components
            }
        })
    }

    fun getDataFromSearch(catDesc: String, queryString: String) {
        componentListA.value = appRepository.getComponentsByCategorySearch(catDesc, "%$queryString%")
    }

    fun getDataFromSearchFilteredMin(catDesc: String, queryString: String, minVal: Long) {
        componentListA.value = appRepository.getComponentsByCategorySearchFilteredMin(catDesc, "%$queryString%", minVal)
    }

    fun getDataFromSearchFilteredMax(catDesc: String, queryString: String, maxVal: Long) {
        componentListA.value = appRepository.getComponentsByCategorySearchFilteredMax(catDesc, "%$queryString%", maxVal)
    }

    fun getDataFromSearchFilteredMinMax(catDesc: String, queryString: String, minVal: Long, maxVal: Long) {
        componentListA.value = appRepository.getComponentsByCategorySearchFilteredMinMax(catDesc, "%$queryString%", minVal, maxVal)
    }
}