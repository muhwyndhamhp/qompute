package com.example.muhwyndham.qompute.utils

import android.content.Context
import com.example.muhwyndham.qompute.data.AppRepository
import com.example.muhwyndham.qompute.data.database.AppDatabase

object InjectorUtils {

    private fun getAppRepository(context: Context) = AppRepository.getInstance(
        AppDatabase.getInstance(context).componentDao())
}