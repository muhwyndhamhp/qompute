package com.github.muhwyndhamhp.qompute.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class BuildingViewModel(private val appRepository: AppRepository) : ViewModel() {

    var build: MutableLiveData<Build> = MutableLiveData()
    val cpuBrand: MutableLiveData<Int> = MutableLiveData()
    val socketType: MutableLiveData<Int> = MutableLiveData()
    val componentPosition: MutableLiveData<Int> = MutableLiveData()
    val componentInBuildPosition: MutableLiveData<Int> = MutableLiveData()
    val componentListA: MutableLiveData<List<Component>> = MutableLiveData()
    val currentSubcategory: MutableLiveData<String> = MutableLiveData()
    val exceptionList = MutableLiveData<MutableList<Throwable>>()
    val tagList = MutableLiveData<MutableList<String>>()

    fun changeComponentCount(itemCount: Int, componentPosition: Int) {
        build.value!!.componentCount!![componentPosition] = itemCount
        updateBuildPrice()
    }

    init {
        componentPosition.value = 99
    }

    fun initiateBuildObject(buildData: Build?) {
        if (buildData == null) {
            build.value = Build(
                0,
                "",
                "",
                mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                mutableListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                mutableListOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                0
            )
        } else {
            build.value = buildData
        }
    }

    fun updateProcessorType() {
        clearAll(0)
        clearAll(1)
        clearAll(8)
    }

    private fun clearAll(i: Int) {
        build.value!!.componentCount!![i] = 1
        build.value!!.componentIds!![i] = ""
        build.value!!.componentName!![i] = ""
        build.value!!.componentPrice!![i] = 0
        updateBuildPrice()
    }


    fun getData(catDesc: String) {
        when (catDesc) {
            "coolerfan", "motherboard", "processor", "memoryram" ->getDataWithArgument(
                catDesc,
                "%${currentSubcategory.value!!}%"
            )
            else -> {
                doAsync { appRepository.getComponentsByCategoryAsc(catDesc, object : AppRepository.LoadDataCallback {
                    override fun onFailed(TAG: String, t: Throwable) {
                        uiThread {
                            exceptionList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, t)
                            tagList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, TAG)
                        }
                    }

                    override fun onSuccess(components: List<Component>) {
                        uiThread { componentListA.value = components }


                    }
                }) }
            }
        }

    }

    fun updateBuildPrice() {
        var currentTotal = 0.toLong()
        for (i in build.value!!.componentPrice!!.indices) {
            currentTotal += (build.value!!.componentPrice!![i] * build.value!!.componentCount!![i])
        }
        val temp = build.value!!
        temp.totalPrice = currentTotal
        build.value  = temp
    }

    private fun getDataWithArgument(catDesc: String, value: String) {
        doAsync {
            appRepository.getComponentsByCategoryAsc(catDesc, value, null, object : AppRepository.LoadDataCallback {
                override fun onFailed(TAG: String, t: Throwable) {
                    uiThread {
                        exceptionList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, t)
                        tagList.value?.set(ERROR_CODE_FAILED_TO_FETCH_PART_1, TAG)
                    }
                }

                override fun onSuccess(components: List<Component>) {
                    uiThread {
                        componentListA.value = components
                    }
                }
            })
        }
    }

    fun deleteComponent(componentListPosition: Int) {
        clearAll(componentListPosition)
    }

    fun setComponentPosition(componentId: Int) {
        componentPosition.value = componentId
    }

    fun getDataFromSearch(catDesc: String, queryString: String) {
        val string = "%$queryString%"
        when (catDesc) {
            "coolerfan", "motherboard", "processor", "memoryram" -> componentListA.value =
                    appRepository.getComponentsByCategorySearch(catDesc, string, "%${currentSubcategory.value!!}%")
            else -> componentListA.value =
                    appRepository.getComponentsByCategorySearch(catDesc, string)
        }
    }

    fun getDataFromSearchFilteredMin(catDesc: String, queryString: String, minVal: Long) {
        val string = "%$queryString%"
        when (catDesc) {
            "coolerfan", "motherboard", "processor", "memoryram" -> componentListA.value =
                    appRepository.getComponentsByCategorySearchFilteredMin(
                        catDesc,
                        string,
                        minVal,
                        "%${currentSubcategory.value!!}%"
                    )
            else -> componentListA.value =
                    appRepository.getComponentsByCategorySearchFilteredMin(catDesc, string, minVal)
        }
    }

    fun getDataFromSearchFilteredMax(catDesc: String, queryString: String, maxVal: Long) {
        val string = "%$queryString%"

        when (catDesc) {
            "coolerfan", "motherboard", "processor", "memoryram" -> componentListA.value =
                    appRepository.getComponentsByCategorySearchFilteredMax(
                        catDesc,
                        string,
                        maxVal,
                        "%${currentSubcategory.value!!}%"
                    )
            else -> componentListA.value =
                    appRepository.getComponentsByCategorySearchFilteredMax(catDesc, string, maxVal)
        }
    }

    fun getDataFromSearchFilteredMinMax(catDesc: String, queryString: String, minVal: Long, maxVal: Long) {
        val string = "%$queryString%"
        when (catDesc) {
            "coolerfan", "motherboard", "processor", "memoryram" -> componentListA.value =
                    appRepository.getComponentsByCategorySearchFilteredMinMax(
                        catDesc,
                        string,
                        minVal,
                        maxVal,
                        "%${currentSubcategory.value!!}%"
                    )
            else -> componentListA.value =
                    appRepository.getComponentsByCategorySearchFilteredMinMax(catDesc, string, minVal, maxVal)
        }
    }

    fun addComponent(component: Component) {
        build.value!!.componentIds!![componentInBuildPosition.value!!] = component.id
        build.value!!.componentName!![componentInBuildPosition.value!!] = component.name
        build.value!!.componentPrice!![componentInBuildPosition.value!!] = component.price.toLong()
        updateBuildPrice()
    }

    fun getComponentName(
        value: Int,
        componentEndPoint: Array<String>,
        subcategories_endpoint: Array<String>,
        socketIntel: Array<String>,
        socketAmd: Array<String>
    ): String {
        return when (value) {
            5 -> {
                currentSubcategory.value = subcategories_endpoint[0]
                componentEndPoint[5]
            }
            101 -> {
                currentSubcategory.value = subcategories_endpoint[3]
                componentEndPoint[1]
            }
            102 -> {
                currentSubcategory.value = subcategories_endpoint[4]
                componentEndPoint[1]
            }
            900 -> {
                currentSubcategory.value = socketIntel[0]
                componentEndPoint[9]
            }
            901 -> {
                currentSubcategory.value = socketIntel[1]
                componentEndPoint[9]
            }
            902 -> {
                currentSubcategory.value = socketIntel[2]
                componentEndPoint[9]
            }
            903 -> {
                currentSubcategory.value = socketIntel[3]
                componentEndPoint[9]
            }
            904 -> {
                currentSubcategory.value = socketIntel[4]
                componentEndPoint[9]
            }
            905 -> {
                currentSubcategory.value = socketIntel[5]
                componentEndPoint[9]
            }
            911 -> {
                currentSubcategory.value = socketAmd[1]
                componentEndPoint[9]
            }
            912 -> {
                currentSubcategory.value = socketAmd[2]
                componentEndPoint[9]
            }
            913 -> {
                currentSubcategory.value = socketAmd[3]
                componentEndPoint[9]
            }
            914 -> {
                currentSubcategory.value = socketAmd[4]
                componentEndPoint[9]
            }
            915 -> {
                currentSubcategory.value = socketAmd[5]
                componentEndPoint[9]
            }
            916 -> {
                currentSubcategory.value = socketAmd[6]
                componentEndPoint[9]
            }
            600 -> {
                currentSubcategory.value = socketIntel[0]
                componentEndPoint[6]
            }
            601 -> {
                currentSubcategory.value = socketIntel[1]
                componentEndPoint[6]
            }
            602 -> {
                currentSubcategory.value = socketIntel[2]
                componentEndPoint[6]
            }
            603 -> {
                currentSubcategory.value = socketIntel[3]
                componentEndPoint[6]
            }
            604 -> {
                currentSubcategory.value = socketIntel[4]
                componentEndPoint[6]
            }
            605 -> {
                currentSubcategory.value = socketIntel[5]
                componentEndPoint[6]
            }
            611 -> {
                currentSubcategory.value = socketAmd[1]
                componentEndPoint[6]
            }
            612 -> {
                currentSubcategory.value = socketAmd[2]
                componentEndPoint[6]
            }
            613 -> {
                currentSubcategory.value = socketAmd[3]
                componentEndPoint[6]
            }
            614 -> {
                currentSubcategory.value = socketAmd[4]
                componentEndPoint[6]
            }
            615 -> {
                currentSubcategory.value = socketAmd[5]
                componentEndPoint[6]
            }
            616 -> {
                currentSubcategory.value = socketAmd[6]
                componentEndPoint[6]
            }
            else -> componentEndPoint[value]
        }
    }

    fun setBuildName(text: Editable?) {
        val temp = build.value!!
        temp.name = text.toString()
        build.value = temp
    }

    fun saveBuild() {
        appRepository.insertBuild(build.value!!)
    }

}