package com.github.muhwyndhamhp.qompute.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.ComponentListViewModel

class ComponentListActivity : AppCompatActivity() {

    private lateinit var viewModel: ComponentListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component_list)

        val factory = InjectorUtils.provideComponentListViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(ComponentListViewModel::class.java)


    }
}
