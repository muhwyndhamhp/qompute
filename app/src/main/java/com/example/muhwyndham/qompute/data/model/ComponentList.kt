package com.example.muhwyndham.qompute.data.model

import java.io.Serializable

class ComponentList(val componentList: List<Component>): Serializable {
    companion object {
        fun createComponentList(componentList: Collection<Component>): ComponentList{
            return ComponentList(componentList as List<Component>)
        }
    }
}