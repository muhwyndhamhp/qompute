package com.github.muhwyndhamhp.qompute.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.data.model.BuildDao
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.data.model.ComponentDao
import com.github.muhwyndhamhp.qompute.network.service.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppRepository private constructor(
    private val componentDao: ComponentDao,
    private val buildDao: BuildDao
) {

    interface LoadDataCallback {
        fun onFailed(TAG: String, t: Throwable)
        fun onSuccess(components: List<Component>)
    }


    fun getComponentsByCategoryAsc(catDec: String, loadDataCallback: LoadDataCallback) {
        if (checkComponentValidity()) {
            loadDataCallback.onSuccess(
                if (catDec == "harddisk") componentDao.getComponentsByCategoryAscHDD(catDec)
                else componentDao.getComponentsByCategoryAsc(catDec)
            )
        } else {
            componentDao.deleteAll()
            reloadData1(object : LoadDataCallback {
                override fun onFailed(TAG: String, t: Throwable) {
                    loadDataCallback.onFailed(TAG, t)
                }

                override fun onSuccess(components: List<Component>) {
                    reloadData2(object : LoadDataCallback {
                        override fun onFailed(TAG: String, t: Throwable) {
                            loadDataCallback.onFailed(TAG, t)
                        }

                        override fun onSuccess(components: List<Component>) {
                            loadDataCallback.onSuccess(
                                if (catDec == "harddisk") componentDao.getComponentsByCategoryAscHDD(catDec)
                                else componentDao.getComponentsByCategoryAsc(catDec)
                            )
                        }

                    })
                }

            })
        }
    }

    fun getComponentsByCategoryAsc(catDec: String, argument1: String, argument2: String?, loadDataCallback: LoadDataCallback) {
        if (checkComponentValidity()) {
            loadDataCallback.onSuccess(
                if (catDec == "harddisk" && argument2 != null) componentDao.getComponentsByCategoryAscHDD(catDec, argument1, argument2)
                else componentDao.getComponentsByCategoryAsc(catDec, argument1)
            )
        } else {
            componentDao.deleteAll()
            reloadData1(object : LoadDataCallback {
                override fun onFailed(TAG: String, t: Throwable) {
                    loadDataCallback.onFailed(TAG, t)
                }

                override fun onSuccess(components: List<Component>) {
                    reloadData2(object : LoadDataCallback {
                        override fun onFailed(TAG: String, t: Throwable) {
                            loadDataCallback.onFailed(TAG, t)
                        }

                        override fun onSuccess(components: List<Component>) {
                            loadDataCallback.onSuccess(
                                if (catDec == "harddisk") componentDao.getComponentsByCategoryAscHDD(catDec)
                                else componentDao.getComponentsByCategoryAsc(catDec)
                            )
                        }

                    })
                }

            })
        }
    }

    private fun checkComponentValidity() =
        if (!componentDao.getAllComponents().isEmpty())
            (System.currentTimeMillis() / 1000 - componentDao.getAllComponents()[0].lastUpdate!!) / (60 * 60 * 24) < 14
        else false

    fun getComponentsByCategorySearch(catDesc: String, string: String) =
        componentDao.getComponentsByCategorySearch(catDesc, string)

    fun getComponentsByCategorySearch(catDesc: String, string: String, argument1: String) =
        componentDao.getComponentsByCategorySearch(catDesc, string, argument1)

    fun getAllBuilds() = buildDao.getAllBuilds()

    fun insertBuild(build: Build) = buildDao.insertSingleBuild(build)

    fun updateBuild(build: Build) = buildDao.updateBuild(build)

    @SuppressLint("CheckResult")
    fun reloadData1(loadDataCallback: LoadDataCallback) {
        val networkService = NetworkService.getInstance()

        networkService.getAll1()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ componentList ->
                for (component in componentList) component.lastUpdate = System.currentTimeMillis() / 1000
                componentDao.insertAll(componentList)
                loadDataCallback.onSuccess(componentList)
            }) {
                loadDataCallback.onFailed(TAG, it)
            }
    }

    @SuppressLint("CheckResult")
    fun reloadData2(loadDataCallback: LoadDataCallback) {
        val networkService = NetworkService.getInstance()

        networkService.getAll2()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ componentList ->
                for (component in componentList) component.lastUpdate = System.currentTimeMillis() / 1000
                componentDao.insertAll(componentList)
                loadDataCallback.onSuccess(componentList)
            }) {
                loadDataCallback.onFailed(TAG, it)
            }
    }

    fun getBuild(buildId: Long) = buildDao.getSingleBuild(buildId)

    fun getComponentsByCategorySearchFilteredMin(
        catDesc: String,
        string: String,
        minValString: Long
    ) = componentDao.getComponentsByCategorySearchFilteredMin(catDesc, string, minValString)

    fun getComponentsByCategorySearchFilteredMin(
        catDesc: String,
        string: String,
        minValString: Long,
        argument1: String
    ) = componentDao.getComponentsByCategorySearchFilteredMin(catDesc, string, minValString, argument1)

    fun getComponentsByCategorySearchFilteredMax(
        catDesc: String,
        string: String,
        maxValString: Long
    ) = componentDao.getComponentsByCategorySearchFilteredMax(catDesc, string, maxValString)

    fun getComponentsByCategorySearchFilteredMax(
        catDesc: String,
        string: String,
        maxValString: Long,
        argument1: String
    ) = componentDao.getComponentsByCategorySearchFilteredMax(catDesc, string, maxValString, argument1)

    fun getComponentsByCategorySearchFilteredMinMax(
        catDesc: String,
        string: String,
        minValString: Long,
        maxValString: Long
    ) = componentDao.getComponentsByCategorySearchFilteredMinMax(catDesc, string, minValString, maxValString)

    fun getComponentsByCategorySearchFilteredMinMax(
        catDesc: String,
        string: String,
        minValString: Long,
        maxValString: Long,
        argument1: String
    ) = componentDao.getComponentsByCategorySearchFilteredMinMax(catDesc, string, minValString, maxValString, argument1)


    companion object {
        private const val TAG = "REPO"

        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(componentDao: ComponentDao, buildDao: BuildDao) =
            instance ?: synchronized(this) { instance ?: AppRepository(componentDao, buildDao).also { instance = it } }
    }


}
