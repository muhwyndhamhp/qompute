package com.github.muhwyndhamhp.qompute.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.BuildPreviewViewModel

class BuildPreviewActivity : AppCompatActivity() {


    lateinit var viewModel: BuildPreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_preview)

        val factory = InjectorUtils.provideBuildPreviewViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(BuildPreviewViewModel::class.java)


    }
}
