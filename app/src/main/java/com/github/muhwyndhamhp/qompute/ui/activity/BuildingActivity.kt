package com.github.muhwyndhamhp.qompute.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingPagerAdapter
import com.github.muhwyndhamhp.qompute.utils.BUILD_ID_DB
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.factory.BuildingViewModelFactory
import kotlinx.android.synthetic.main.activity_building.*

class BuildingActivity : AppCompatActivity() {


    private var viewModelFactory: BuildingViewModelFactory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)

        val viewPagerAdapter = BuildingPagerAdapter(this, 2, supportFragmentManager)

        view_pager_build.adapter = viewPagerAdapter
    }

    fun getViewModelFactory() =
        viewModelFactory ?: InjectorUtils.provideBuildingViewModelFactory(this).also { viewModelFactory = it }

    fun getBuildObjectIntent() = intent.getLongExtra(BUILD_ID_DB, 0)

    fun changeFragment(fragmentId: Int) {
        view_pager_build.currentItem = fragmentId
    }
}
