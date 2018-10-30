package com.example.muhwyndham.qompute.network.service

import android.database.Observable
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.network.client.RetrofitClient
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function9

class NetworkService {
    private var networkApi: NetworkApi? = null

    fun getAll1(): List<Component>? {

        val componentList = mutableListOf<Component>()

        Single.zip(
            getApi().getCasing(),
                    getApi().getCoolerfan(),
                    getApi().getHarddisk(),
                    getApi().getKeyboard(),
                    getApi().getLcd(),
                    getApi().getRam(),
                    getApi().getMotherboard(),
                    getApi().getOptical(),
                    getApi().getPrinter(),
            Function9 {
                casing: List<Component>,
                coolerfan: List<Component>,
                harddisk: List<Component>,
                keyboard: List<Component>,
                lcd: List<Component>,
                ram: List<Component>,
                motherboard: List<Component>,
                optical: List<Component>,
                printer:List<Component>
            ->
            componentList.addAll(casing)
            componentList.addAll(coolerfan)
            componentList.addAll(harddisk)
            componentList.addAll(keyboard)
            componentList.addAll(lcd)
            componentList.addAll(ram)
            componentList.addAll(motherboard)
            componentList.addAll(optical)
            componentList.addAll(printer)
        }).subscribe({

        }){

        }
        return componentList
    }


    private fun getApi(): NetworkApi {
        return networkApi ?: RetrofitClient.getInstance().create(NetworkApi::class.java).also { networkApi = it }!!
    }

    companion object {
        @Volatile
        private var instance: NetworkService? = null

        fun getInstance() = instance ?: synchronized(this){
            instance ?: NetworkService().also { instance = it }
        }
    }
}