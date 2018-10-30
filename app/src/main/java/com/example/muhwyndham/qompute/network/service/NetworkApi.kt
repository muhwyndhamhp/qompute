package com.example.muhwyndham.qompute.network.service

import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.utils.API_ROUTE
import retrofit2.http.GET

interface NetworkApi {

    @GET("$API_ROUTE/casing.json")
    fun getCasing(): List<Component>

    @GET("$API_ROUTE/coolerfan.json")
    fun getCoolerfan(): List<Component>

    @GET("$API_ROUTE/harddisk.json")
    fun getHarddisk(): List<Component>

    @GET("$API_ROUTE/keyboard.json")
    fun getKeyboard(): List<Component>

    @GET("$API_ROUTE/lcd.json")
    fun getLcd(): List<Component>

    @GET("$API_ROUTE/memoryram.json")
    fun getRam(): List<Component>

    @GET("$API_ROUTE/motherboard.json")
    fun getMotherboard(): List<Component>

    @GET("$API_ROUTE/optical.json")
    fun getOptical(): List<Component>

    @GET("$API_ROUTE/printer.json")
    fun getPrinter(): List<Component>

    @GET("$API_ROUTE/processor.json")
    fun getProcessor(): List<Component>

    @GET("$API_ROUTE/psu.json")
    fun getPsu(): List<Component>

    @GET("$API_ROUTE/soundcard.json")
    fun getSoundcard(): List<Component>

    @GET("$API_ROUTE/speaker.json")
    fun getSpeaker(): List<Component>

    @GET("$API_ROUTE/ssd.json")
    fun getSsd(): List<Component>

    @GET("$API_ROUTE/vga.json")
    fun getGraphic(): List<Component>
}