package com.github.muhwyndhamhp.qompute.data

import android.annotation.SuppressLint
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

    fun getAllBuilds() = buildDao.getAllBuilds()

    fun insertBuild(build: Build) = buildDao.insertSingleBuild(build)


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

    fun getBuild(buildId: Long): Build? {
        return buildDao.getSingleBuild(buildId)
    }

    companion object {
        private const val TAG = "REPO"

        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(componentDao: ComponentDao, buildDao: BuildDao) =
            instance ?: synchronized(this) { instance ?: AppRepository(componentDao, buildDao).also { instance = it } }
    }


}
