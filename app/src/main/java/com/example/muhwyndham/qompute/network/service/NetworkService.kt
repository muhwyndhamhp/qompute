package com.example.muhwyndham.qompute.network.service

import android.util.Log
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.data.model.ComponentList
import com.example.muhwyndham.qompute.network.client.RetrofitClient
import io.reactivex.Single
import io.reactivex.functions.Function6
import io.reactivex.functions.Function9

class NetworkService {
    private var networkApi: NetworkApi? = null

    fun getAll1() =
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
            Function9 { casing: ComponentList,
                        coolerfan: ComponentList,
                        harddisk: ComponentList,
                        keyboard: ComponentList,
                        lcd: ComponentList,
                        ram: ComponentList,
                        motherboard: ComponentList,
                        optical: ComponentList,
                        printer: ComponentList
                ->
                val list = mutableListOf<Component>()
                list.addAll(casing.componentList)
                Log.e(TAG, casing.componentList[0].name)
                list.addAll(coolerfan.componentList)
                list.addAll(harddisk.componentList)
                list.addAll(keyboard.componentList)
                list.addAll(lcd.componentList)
                list.addAll(ram.componentList)
                list.addAll(motherboard.componentList)
                list.addAll(optical.componentList)
                list.addAll(printer.componentList)
                return@Function9 list
            })!!

    fun getAll2() =
        Single.zip(
            getApi().getProcessor(),
            getApi().getPsu(),
            getApi().getSoundcard(),
            getApi().getSpeaker(),
            getApi().getSsd(),
            getApi().getGraphic(),
            Function6 { processor: ComponentList,
                        psu: ComponentList,
                        soundcard: ComponentList,
                        speaker: ComponentList,
                        ssd: ComponentList,
                        graphic: ComponentList
                ->
                val list = mutableListOf<Component>()
                list.addAll(processor.componentList)
                list.addAll(psu.componentList)
                list.addAll(soundcard.componentList)
                list.addAll(speaker.componentList)
                list.addAll(ssd.componentList)
                list.addAll(graphic.componentList)
                return@Function6 list
            }
        )!!


    private fun getApi(): NetworkApi {
        return networkApi ?: RetrofitClient.getInstance().create(NetworkApi::class.java).also { networkApi = it }!!
    }

    companion object {
        @Volatile
        private var instance: NetworkService? = null

        const val TAG = "Network_Service"

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: NetworkService().also { instance = it }
        }
    }
}