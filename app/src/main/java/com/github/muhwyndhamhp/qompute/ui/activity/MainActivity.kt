@file:Suppress("DEPRECATION")

package com.github.muhwyndhamhp.qompute.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.ui.fragment.BrowseFragment
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildsFragment
import com.github.muhwyndhamhp.qompute.ui.fragment.ProfileFragment
import com.github.muhwyndhamhp.qompute.utils.BUILD_DATA_CODE
import com.github.muhwyndhamhp.qompute.utils.CATEGORY_CODE
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.utils.MainRecyclerViewCommunicator
import com.github.muhwyndhamhp.qompute.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), MainRecyclerViewCommunicator {
    override fun startBuildPreviewActivity(build: Build) {
        val intent = Intent(this, BuildPreviewActivity::class.java)
        intent.putExtra(BUILD_DATA_CODE, build)
        startActivity(intent)
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation(bottomNavigationView)

        val factory = InjectorUtils.provideMainViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.fragmentPosition.observe(this, Observer { replaceFragment(selectFragment(it)) })
        viewModel.setFragmentPosition(0)
    }


    override fun getComponentEndpointArray(): List<String> {
        return resources.getStringArray(R.array.component_endpoint).toList()
    }

    override fun getComponentCategories(): List<String> {
        return resources.getStringArray(R.array.component_categories).toList()
    }

    private fun replaceFragment(selectFragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, selectFragment)
        ft.commit()
    }

    private fun selectFragment(it: Int): Fragment {
        return when (it) {
            0 -> BuildsFragment.newInstance()
            1 -> BrowseFragment.newInstance()
            2 -> ProfileFragment.newInstance()
            else -> BuildsFragment.newInstance()
        }

    }

    private fun setupBottomNavigation(bottomNavigationView: AHBottomNavigation) {
        val context = this
        val ahBottomNavigationAdapter = AHBottomNavigationAdapter(this, R.menu.menu_main)
        ahBottomNavigationAdapter.setupWithBottomNavigation(bottomNavigationView)
        bottomNavigationView.apply {
            accentColor = ContextCompat.getColor(context, R.color.colorAccent)
            inactiveColor = ContextCompat.getColor(context, R.color.defaultUnselected)
            defaultBackgroundColor = ContextCompat.getColor(context, R.color.white)
            setOnTabSelectedListener { position, _ ->
                viewModel.setFragmentPosition(position)
                true
            }
        }
    }

    override fun showLoading(message: String, title: String) {
        progressDialog = indeterminateProgressDialog(message = message, title = title)
        progressDialog.setProgressStyle(R.style.MyAlertDialogStyle)
        progressDialog.show()
    }

    override fun dismissLoading() {
        Timer().schedule(2000) {
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }

    override fun startComponentListActivity(categoryCode: String) {
        val intent = Intent(this, ComponentListActivity::class.java)
        intent.putExtra(CATEGORY_CODE, categoryCode)
        this.startActivity(intent)
    }

}
