package com.github.muhwyndhamhp.qompute.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.muhwyndhamhp.qompute.data.AppRepository
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.BASEURL1
import com.github.muhwyndhamhp.qompute.utils.BASEURL2
import com.github.muhwyndhamhp.qompute.utils.BASEURL3

class ComponentDetailViewModel(
    val appRepository: AppRepository,
    val component: Component
) : ViewModel() {
    fun getAffiliateLink(): String? {
        return when (component.categoryDescription) {
            "casing" -> buildLink("%2Faksesoris-226%2Ftas-case-238", formatText(component.name))
            else -> buildLink("", formatText(component.name))
        }
    }

    private fun formatText(name: String): String {
        val b = name.replace("(", "%2528")
        val c = b.replace(")", "%2529")
        val d = c.split(" ").map { it.trim() }
        var e = String()
        for (i in d.indices) {
            if (i >= 7) {
                break
            }
            e += "${d[i]}%2B"
        }
        return e
    }

    private fun buildLink(s: String, replace: String) = "$BASEURL1$s$BASEURL2$replace$BASEURL3"

    val componentData: MutableLiveData<Component> = MutableLiveData()

    init {
        componentData.value = component
    }
}