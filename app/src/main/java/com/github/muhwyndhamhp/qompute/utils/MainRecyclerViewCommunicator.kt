package com.github.muhwyndhamhp.qompute.utils

import com.github.muhwyndhamhp.qompute.data.model.Build

interface MainRecyclerViewCommunicator {
    fun getComponentEndpointArray(): List<String>
    fun showLoading(message: String, title: String)
    fun dismissLoading()
    fun startComponentListActivity(categoryCode: String)
    fun getComponentCategories(): List<String>
    fun startBuildPreviewActivity(build: Build)
}