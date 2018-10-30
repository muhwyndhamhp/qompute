package com.example.muhwyndham.qompute.network.service

import android.database.Observable
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.utils.API_ROUTE
import io.reactivex.SingleSource
import retrofit2.http.GET

interface NetworkApi {

    @GET("$API_ROUTE/casing.json")
    fun getCasing(): SingleSource<List<Component>>

    @GET("$API_ROUTE/coolerfan.json")
    fun getCoolerfan(): SingleSource<List<Component>>

    @GET("$API_ROUTE/harddisk.json")
    fun getHarddisk(): SingleSource<List<Component>>

    @GET("$API_ROUTE/keyboard.json")
    fun getKeyboard(): SingleSource<List<Component>>

    @GET("$API_ROUTE/lcd.json")
    fun getLcd(): SingleSource<List<Component>>

    @GET("$API_ROUTE/memoryram.json")
    fun getRam(): SingleSource<List<Component>>

    @GET("$API_ROUTE/motherboard.json")
    fun getMotherboard(): SingleSource<List<Component>>

    @GET("$API_ROUTE/optical.json")
    fun getOptical(): SingleSource<List<Component>>

    @GET("$API_ROUTE/printer.json")
    fun getPrinter(): SingleSource<List<Component>>

    @GET("$API_ROUTE/processor.json")
    fun getProcessor(): SingleSource<List<Component>>

    @GET("$API_ROUTE/psu.json")
    fun getPsu(): SingleSource<List<Component>>

    @GET("$API_ROUTE/soundcard.json")
    fun getSoundcard(): SingleSource<List<Component>>

    @GET("$API_ROUTE/speaker.json")
    fun getSpeaker(): SingleSource<List<Component>>

    @GET("$API_ROUTE/ssd.json")
    fun getSsd(): SingleSource<List<Component>>

    @GET("$API_ROUTE/vga.json")
    fun getGraphic(): SingleSource<List<Component>>
}