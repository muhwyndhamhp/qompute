package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_2

class ComponentListViewModel(val appRepository: AppRepository) : ViewModel() {

    val componentList = MutableLiveData<List<Component>>()
    val exceptionList = MutableLiveData<MutableList<Throwable>>()
    val tagList = MutableLiveData<MutableList<String>>()

    fun getData(catDesc: String) {
        if (appRepository.getAllComponents().isEmpty())
            appRepository.reloadData1(object : AppRepository.LoadDataCallback {
                override fun onFailed(TAG: String, t: Throwable) {
                    exceptionList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, t)
                    tagList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, TAG)
                }

                override fun onSuccess(components: List<Component>) {
                    val componentList1 = components as MutableList<Component>
                    appRepository.reloadData2(object : AppRepository.LoadDataCallback {
                        override fun onFailed(TAG: String, t: Throwable) {
                            exceptionList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_2, t)
                            tagList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_2, TAG)
                        }

                        override fun onSuccess(components: List<Component>) {
                            componentList1.addAll(components)
                            componentList.value = componentList1
                        }

                    })
                }

            })
        else componentList.value = appRepository.getComponentsByCategoryAsc(catDesc)
    }

    fun getDataFromSearch(catDesc: String, queryString: String){
        val string = "%$queryString%"
        componentList.value = appRepository.getComponentsByCategorySearch(catDesc, string)
    }

}