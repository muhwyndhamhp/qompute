package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1

class BuildingViewModel(private val appRepository: AppRepository) : ViewModel() {

    var build: MutableLiveData<Build> = MutableLiveData()
    val cpuBrand: MutableLiveData<Int> = MutableLiveData()
    val socketType: MutableLiveData<Int> = MutableLiveData()
    val componentPosition: MutableLiveData<Int> = MutableLiveData()
    val componentListA: MutableLiveData<List<Component>> = MutableLiveData()
    val exceptionList = MutableLiveData<MutableList<Throwable>>()
    val tagList = MutableLiveData<MutableList<String>>()

    fun changeComponentCount(itemCount: Int, componentPosition: Int) {
        val temp = build
        temp.value!!.componentCount!![componentPosition] = itemCount
        appRepository.updateBuild(temp.value!!)
    }
    init {
        componentPosition.value = 99
    }

    fun initiateBuildObject(intExtra: Long) {
        if (intExtra == 0.toLong()) {
            build.value = Build(
                0,
                "",
                "",
                mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                mutableListOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                0
            )
        } else {
            build = appRepository.getBuild(intExtra) as MutableLiveData<Build>
        }
    }

    fun updateProcessorType() {
        clearAll(0)
        clearAll(1)
        clearAll(8)
    }

    private fun clearAll(i: Int) {
        val temp = build
        if (temp.value != null) {
            temp.value!!.componentCount!![i] = 1
            temp.value!!.componentIds!![i] = ""
            temp.value!!.componentName!![i] = ""
            appRepository.updateBuild(temp.value!!)
        }
    }


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

    fun deleteComponent(componentListPosition: Int) {
        clearAll(componentListPosition)
    }

    fun setComponentPosition(componentId: Int) {
        componentPosition.value = componentId
    }

    fun getDataFromSearch(catDesc: String, queryString: String) {
        val string = "%$queryString%"
        componentListA.value = appRepository.getComponentsByCategorySearch(catDesc, string)
    }

    fun getDataFromSearchFilteredMin(catDesc: String, queryString: String, minVal: Long) {
        val string = "%$queryString%"
        componentListA.value = appRepository.getComponentsByCategorySearchFilteredMin(catDesc, string, minVal)
    }

    fun getDataFromSearchFilteredMax(catDesc: String, queryString: String, maxVal: Long) {
        val string = "%$queryString%"
        componentListA.value = appRepository.getComponentsByCategorySearchFilteredMax(catDesc, string, maxVal)
    }

    fun getDataFromSearchFilteredMinMax(catDesc: String, queryString: String, minVal: Long, maxVal: Long) {
        val string = "%$queryString%"
        componentListA.value = appRepository.getComponentsByCategorySearchFilteredMinMax(catDesc, string, minVal, maxVal)
    }
}