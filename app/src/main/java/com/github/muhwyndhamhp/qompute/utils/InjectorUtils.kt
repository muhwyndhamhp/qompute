package com.github.muhwyndhamhp.qompute.utils

import android.content.Context
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.database.AppDatabase
import com.github.muhwyndhamhp.qompute.viewmodel.factory.MainViewModelFactory

object InjectorUtils {

    private fun getAppRepository(context: Context) = AppRepository.getInstance(
        AppDatabase.getInstance(context).componentDao(),
        AppDatabase.getInstance(context).buildDao())

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getAppRepository(context)
        return MainViewModelFactory(repository)
    }
}