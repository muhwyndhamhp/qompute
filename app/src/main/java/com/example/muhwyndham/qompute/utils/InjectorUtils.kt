package com.example.muhwyndham.qompute.utils

import android.content.Context
import com.example.muhwyndham.qompute.data.AppRepository
import com.example.muhwyndham.qompute.data.database.AppDatabase
import com.example.muhwyndham.qompute.viewmodel.MainViewModelFactory

object InjectorUtils {

    private fun getAppRepository(context: Context) = AppRepository.getInstance(
        AppDatabase.getInstance(context).componentDao())

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory{
        val repository = getAppRepository(context)
        return MainViewModelFactory(repository)
    }
}