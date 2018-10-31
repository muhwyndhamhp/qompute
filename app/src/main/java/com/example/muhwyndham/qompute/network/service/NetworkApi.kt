package com.example.muhwyndham.qompute.network.service

import android.database.Observable
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.data.model.ComponentList
import com.example.muhwyndham.qompute.utils.API_ROUTE
import io.reactivex.Single
import io.reactivex.SingleSource
import retrofit2.http.GET

interface NetworkApi {

    @GET("$API_ROUTE/casing.json")
    fun getCasing(): Single<ComponentList>

    @GET("$API_ROUTE/coolerfan.json")
    fun getCoolerfan(): Single<ComponentList>

    @GET("$API_ROUTE/harddisk.json")
    fun getHarddisk(): Single<ComponentList>

    @GET("$API_ROUTE/keyboard.json")
    fun getKeyboard(): Single<ComponentList>

    @GET("$API_ROUTE/lcd.json")
    fun getLcd(): Single<ComponentList>

    @GET("$API_ROUTE/memoryram.json")
    fun getRam(): Single<ComponentList>

    @GET("$API_ROUTE/motherboard.json")
    fun getMotherboard(): Single<ComponentList>

    @GET("$API_ROUTE/optical.json")
    fun getOptical(): Single<ComponentList>

    @GET("$API_ROUTE/printer.json")
    fun getPrinter(): Single<ComponentList>

    @GET("$API_ROUTE/processor.json")
    fun getProcessor(): Single<ComponentList>

    @GET("$API_ROUTE/psu.json")
    fun getPsu(): Single<ComponentList>

    @GET("$API_ROUTE/soundcard.json")
    fun getSoundcard(): Single<ComponentList>

    @GET("$API_ROUTE/speaker.json")
    fun getSpeaker(): Single<ComponentList>

    @GET("$API_ROUTE/ssd.json")
    fun getSsd(): Single<ComponentList>

    @GET("$API_ROUTE/vga.json")
    fun getGraphic(): Single<ComponentList>
}