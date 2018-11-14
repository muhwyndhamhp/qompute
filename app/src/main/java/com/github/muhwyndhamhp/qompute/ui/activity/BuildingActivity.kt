package com.github.muhwyndhamhp.qompute.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingPagerAdapter
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildSummaryFragment
import com.github.muhwyndhamhp.qompute.utils.BUILD_ID_DB
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.factory.BuildingViewModelFactory
import kotlinx.android.synthetic.main.activity_building.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.util.*
import kotlin.concurrent.schedule

class BuildingActivity : AppCompatActivity() {


    private var viewModelFactory: BuildingViewModelFactory? = null
    private lateinit var progressDialog: ProgressDialog
    private var fragmentId: Int? = 0

    private lateinit var viewPagerAdapter: BuildingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)

        viewPagerAdapter = BuildingPagerAdapter(this, 2, supportFragmentManager)

        view_pager_build.adapter = viewPagerAdapter
    }

    fun getViewModelFactory() =
        viewModelFactory ?: InjectorUtils.provideBuildingViewModelFactory(this).also { viewModelFactory = it }

    fun getBuildObjectIntent() = intent.getLongExtra(BUILD_ID_DB, 0)

    fun changeFragment(fragmentId: Int, position: Int?) {
        view_pager_build.currentItem = fragmentId
        this.fragmentId = fragmentId
        Handler().postDelayed({
            if (position != null) {
                updateList(position)
            }
        }, 200)
    }

    private fun updateList(position: Int) {
        val fragment = supportFragmentManager.findFragmentByTag("android:switcher:${R.id.view_pager_build}:${view_pager_build.currentItem}") as BuildSummaryFragment
        fragment.updateList(position)
    }

    fun showLoading(body: String, title: String) {
        progressDialog = indeterminateProgressDialog(message = body, title = title)
        progressDialog.setProgressStyle(R.style.MyAlertDialogStyle)
        progressDialog.show()
    }

    fun dismissLoading() {
        Timer().schedule(2000) {
            if (progressDialog.isShowing) progressDialog.dismiss()
        }

    }

    override fun onBackPressed() {
        if(fragmentId == 1){
            changeFragment(0, null)
        }
        else{
            super.onBackPressed()
        }
    }
}
