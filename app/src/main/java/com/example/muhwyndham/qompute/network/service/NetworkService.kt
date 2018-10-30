package com.example.muhwyndham.qompute.network.service

import com.example.muhwyndham.qompute.data.model.Component
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
            Function9 { casing: List<Component>,
                        coolerfan: List<Component>,
                        harddisk: List<Component>,
                        keyboard: List<Component>,
                        lcd: List<Component>,
                        ram: List<Component>,
                        motherboard: List<Component>,
                        optical: List<Component>,
                        printer: List<Component>
                ->
                val list = mutableListOf<Component>()
                list.addAll(casing)
                list.addAll(coolerfan)
                list.addAll(harddisk)
                list.addAll(keyboard)
                list.addAll(lcd)
                list.addAll(ram)
                list.addAll(motherboard)
                list.addAll(optical)
                list.addAll(printer)
                return@Function9 list
            })

    fun getAll2() =
        Single.zip(
            getApi().getProcessor(),
            getApi().getPsu(),
            getApi().getSoundcard(),
            getApi().getSpeaker(),
            getApi().getSsd(),
            getApi().getGraphic(),
            Function6 { processor: List<Component>,
                        psu: List<Component>,
                        soundcard: List<Component>,
                        speaker: List<Component>,
                        ssd: List<Component>,
                        graphic: List<Component>
                ->
                val list = mutableListOf<Component>()
                list.addAll(processor)
                list.addAll(psu)
                list.addAll(soundcard)
                list.addAll(speaker)
                list.addAll(ssd)
                list.addAll(graphic)
                return@Function6 list
            }
        )


    private fun getApi(): NetworkApi {
        return networkApi ?: RetrofitClient.getInstance().create(NetworkApi::class.java).also { networkApi = it }!!
    }

    companion object {
        @Volatile
        private var instance: NetworkService? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: NetworkService().also { instance = it }
        }
    }
}