package com.github.muhwyndhamhp.qompute.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingPagerAdapter
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildComponentSelectFragment
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildSummaryFragment
import com.github.muhwyndhamhp.qompute.utils.BUILD_ID_DB
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import com.github.muhwyndhamhp.qompute.viewmodel.factory.BuildingViewModelFactory
import kotlinx.android.synthetic.main.activity_building.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat
import java.util.*
import kotlin.concurrent.schedule

class BuildingActivity : AppCompatActivity() {


    private var viewModelFactory: BuildingViewModelFactory? = null
    private lateinit var progressDialog: ProgressDialog
    private var fragmentId: Int? = 0

    private lateinit var viewPagerAdapter: BuildingPagerAdapter
    lateinit var viewModel: BuildingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)

        viewModelFactory ?: InjectorUtils.provideBuildingViewModelFactory(this).also { viewModelFactory = it }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BuildingViewModel::class.java)
        viewModel.initiateBuildObject(getBuildObjectIntent())

        viewModel.build.observe(this, Observer {
            updateUi(it)
        })

        viewPagerAdapter = BuildingPagerAdapter(this, 2, supportFragmentManager)
        view_pager_build.adapter = viewPagerAdapter

        bt_edit_build_name.onClick {
            val dialog  = Dialog(this@BuildingActivity)
            dialog.setContentView(R.layout.dialog_buildname_edit)
            dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
            val edittext = dialog.findViewById<EditText>(R.id.et_build_name)
            val button = dialog.findViewById<Button>(R.id.bt_save_build_name)


            viewModel.build.observe(this@BuildingActivity, Observer { build ->
                edittext.setText(build.name)
            })
            button.onClick {
                viewModel.setBuildName(edittext.text)
                if(dialog.isShowing) dialog.dismiss()
            }

            dialog.show()

        }

        bt_build_save.onClick {
            viewModel.saveBuild()
            onBackPressed()
            finish()
        }
    }

    private fun updateUi(it: Build) {
        tv_name.text = if (it.name == "") "Rakitan Baru" else it.name
            tv_price.text = NumberFormat
                .getCurrencyInstance(Locale("in", "ID"))
                .format(it.totalPrice)
    }

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

    fun updateList(position: Int) {
        val fragment = supportFragmentManager
            .findFragmentByTag("android:switcher:${R.id.view_pager_build}:${view_pager_build.currentItem}") as BuildSummaryFragment
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
        if (fragmentId == 1) {
            changeFragment(0, null)
        } else {
            super.onBackPressed()
        }
    }

    fun setComponentPosition(component: Component) {
        val fragment1 = supportFragmentManager
            .findFragmentByTag("android:switcher:${R.id.view_pager_build}:${view_pager_build.currentItem}") as BuildComponentSelectFragment
        fragment1.addComponent(component)
    }
}
