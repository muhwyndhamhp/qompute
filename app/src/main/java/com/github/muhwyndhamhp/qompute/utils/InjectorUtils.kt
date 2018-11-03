package com.github.muhwyndhamhp.qompute.utils

import android.content.Context
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.database.AppDatabase
import com.github.muhwyndhamhp.qompute.viewmodel.factory.BrowseViewModelFactory
import com.github.muhwyndhamhp.qompute.viewmodel.factory.ComponentDetailViewModelFactory
import com.github.muhwyndhamhp.qompute.viewmodel.factory.ComponentListViewModelFactory
import com.github.muhwyndhamhp.qompute.viewmodel.factory.MainViewModelFactory

object InjectorUtils {

    private fun getAppRepository(context: Context) = AppRepository.getInstance(
        AppDatabase.getInstance(context).componentDao(),
        AppDatabase.getInstance(context).buildDao())

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getAppRepository(context)
        return MainViewModelFactory(repository)
    }

    fun provideBrowseViewModelFactory(context: Context): BrowseViewModelFactory{
        val repository = getAppRepository(context)
        return BrowseViewModelFactory(repository)
    }

    fun provideComponentListViewModelFactory(context: Context): ComponentListViewModelFactory{
        val repository = getAppRepository(context)
        return ComponentListViewModelFactory(repository)
    }

    fun provideComponentDetailViewModelFactory(context: Context): ComponentDetailViewModelFactory{
        val repository = getAppRepository(context)
        return ComponentDetailViewModelFactory(repository)
    }
}