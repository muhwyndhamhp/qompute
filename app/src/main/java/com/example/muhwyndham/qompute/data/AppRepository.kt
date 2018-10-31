package com.example.muhwyndham.qompute.data

import android.annotation.SuppressLint
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.data.model.ComponentDao
import com.example.muhwyndham.qompute.network.service.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppRepository private constructor(
    private  val componentDao: ComponentDao
) {

    interface LoadDataCallback{
        fun onFailed(TAG: String, t: Throwable)
        fun onSuccess(components: List<Component>)
    }

    fun getAllComponents() = componentDao.getAllComponents()

    fun getComponentsPaged() = componentDao.getComponentsPaged()

    fun getSingleComponent(id: Int) = componentDao.getSingleComponent(id)

    fun getSingleComponent(name: String) = componentDao.getSingleComponent(name)

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

        networkService.getAll2().subscribe({
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

        fun getInstance(componentDao: ComponentDao) = instance ?: synchronized(this){ instance ?: AppRepository(componentDao).also  { instance = it }}
    }


}
