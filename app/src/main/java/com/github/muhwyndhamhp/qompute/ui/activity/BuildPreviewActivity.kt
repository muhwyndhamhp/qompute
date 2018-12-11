package com.github.muhwyndhamhp.qompute.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildPreviewAdapter
import com.github.muhwyndhamhp.qompute.utils.BUILD_DATA_CODE
import com.github.muhwyndhamhp.qompute.utils.BUILD_ID_DB
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.BuildPreviewViewModel
import kotlinx.android.synthetic.main.activity_build_preview.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat
import java.util.*

class BuildPreviewActivity : AppCompatActivity() {


    lateinit var viewModel: BuildPreviewViewModel
    lateinit var adapter: BuildPreviewAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_preview)

        val factory = InjectorUtils.provideBuildPreviewViewModelFactory(
            this,
            intent.getSerializableExtra(BUILD_DATA_CODE) as Build
        )
        viewModel = ViewModelProviders.of(this, factory).get(BuildPreviewViewModel::class.java)

        viewModel.buildData.observe(this, Observer {
            prepareRecyclerView(it)

            tv_build_title_name.text = if (it.name != "") it.name else "Rakitan Baru"
            tv_build_title_price.text = NumberFormat
                .getCurrencyInstance(Locale("in", "ID"))
                .format(it.totalPrice)
        })

        bt_edit_build_name.onClick {
            val intent = Intent(this@BuildPreviewActivity, BuildingActivity::class.java)
            intent.putExtra(BUILD_ID_DB, viewModel.build)
            startActivity(intent)
        }

    }

    private fun prepareRecyclerView(build: Build) {
        recyclerView = recycler_view_components
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = BuildPreviewAdapter(this, build)
        recyclerView.adapter = adapter
        recyclerView.scheduleLayoutAnimation()
    }
}
