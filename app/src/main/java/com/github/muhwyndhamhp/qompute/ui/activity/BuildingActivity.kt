package com.github.muhwyndhamhp.qompute.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingAdapter
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.activity_building.*

class BuildingActivity : AppCompatActivity() {


    private lateinit var adapter: BuildingAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var viewModel: BuildingViewModel
    private lateinit var componentCategoryName: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)

        val factory = InjectorUtils.provideBuildingViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(BuildingViewModel::class.java)
        setCategoryList()

        setProcessorType()
        prepareComponentRecyclerView()
    }

    private fun setCategoryList() {
        componentCategoryName = getComponentTypeList() as MutableList<String>
        viewModel.build.observe(this, Observer { buildData ->
            if(buildData != null)
                for(i in buildData.componentCount!!.indices){
                    if(buildData.componentIds!![i] != "") componentCategoryName[i] = buildData.componentName!![i]
                }
            if(::adapter.isInitialized) adapter.updateList(componentCategoryName)
        })
    }

    private fun prepareComponentRecyclerView() {
        recyclerview = component_list_recycler_view
        recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = BuildingAdapter(this, componentCategoryName, viewModel)
        recyclerview.setItemViewCacheSize(20)
        recyclerview.adapter = adapter
    }

    private fun getComponentTypeList() = resources.getStringArray(R.array.build_component_list).toList()


    private fun setProcessorType() {
        processor_switch.setOnSwitchListener { position, _ ->
            when (position) {
                0 -> {
                    socket_switch_intel.visibility = VISIBLE; socket_switch_amd.visibility = GONE
                }
                1 -> {
                    socket_switch_intel.visibility = GONE; socket_switch_amd.visibility = VISIBLE
                }
            }
        }
    }
}
