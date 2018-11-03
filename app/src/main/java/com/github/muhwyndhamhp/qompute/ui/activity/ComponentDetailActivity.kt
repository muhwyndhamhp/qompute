package com.github.muhwyndhamhp.qompute.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.ComponentDetailViewModel

class ComponentDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ComponentDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component_detail)

        val factory = InjectorUtils.provideComponentDetailViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(ComponentDetailViewModel::class.java)


    }
}
