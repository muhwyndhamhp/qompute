package com.github.muhwyndhamhp.qompute.data

import android.annotation.SuppressLint
import com.github.muhwyndhamhp.qompute.data.model.BuildDao
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.data.model.ComponentDao
import com.github.muhwyndhamhp.qompute.network.service.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppRepository private constructor(
    private  val componentDao: ComponentDao,
    private val buildDao: BuildDao
) {

    interface LoadDataCallback{
        fun onFailed(TAG: String, t: Throwable)
        fun onSuccess(components: List<Component>)
    }

    fun getAllComponents() = componentDao.getAllComponents()

    fun getComponentsPaged() = componentDao.getComponentsPaged()

    fun getSingleComponent(id: Int) = componentDao.getSingleComponent(id)

    fun getSingleComponent(name: String) = componentDao.getSingleComponent(name)

    fun getComponentByBrandDescriptionAsc(brandDesc: String) = componentDao.getComponentsByBrandDescriptionAsc(brandDesc)

    fun getComponentsByCategoryAsc(catDec: String): List<Component> {
        return if(catDec == "harddisk") componentDao.getComponentsByCategoryAscHDD(catDec)
        else componentDao.getComponentsByCategoryAsc(catDec)
    }

    fun getComponentByBrandDescriptionDesc(brandDesc: String) = componentDao.getComponentsByBrandDescriptionDesc(brandDesc)

    fun getComponentsByCategoryDesc(catDec: String) = componentDao.getComponentsByCategoryDesc(catDec)

    fun getComponentsByCategorySearch(catDesc: String, string: String) = componentDao.getComponentsByCategorySearch(catDesc, string)

    fun getAllBuilds() = buildDao.getAllBuilds()

    fun getBuildsPaged() = buildDao.getBuildsPaged()

    fun getSingleBuild(id: Int) = buildDao.getSingleBuild(id)

    fun getSingleBuild(name: String) = buildDao.getSingleBuild(name)

    @SuppressLint("CheckResult")
    fun reloadData1(loadDataCallback: LoadDataCallback){
        val networkService = NetworkService.getInstance()

        networkService.getAll1()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            componentList ->
            componentDao.insertAll(componentList)
            loadDataCallback.onSuccess(componentList)
        }){
            loadDataCallback.onFailed(TAG, it)
        }
    }

    @SuppressLint("CheckResult")
    fun reloadData2(loadDataCallback: LoadDataCallback){
        val networkService = NetworkService.getInstance()

        networkService.getAll2()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            componentList ->
            componentDao.insertAll(componentList)
            loadDataCallback.onSuccess(componentList)
        }){
            loadDataCallback.onFailed(TAG, it)
        }
    }

    companion object {
        private const val TAG = "REPO"

        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(componentDao: ComponentDao, buildDao: BuildDao) = instance ?: synchronized(this){ instance ?: AppRepository(componentDao, buildDao).also  { instance = it }}
    }


}
