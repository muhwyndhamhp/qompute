package com.github.muhwyndhamhp.qompute.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingAdapter
import kotlinx.android.synthetic.main.activity_building.*

class BuildingActivity : AppCompatActivity() {


    private lateinit var adapter: BuildingAdapter
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)

        setProcessorType()
        prepareComponentRecyclerView()
    }

    private fun prepareComponentRecyclerView() {
        recyclerview = component_list_recycler_view
        recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = BuildingAdapter(this, getComponentTypeList())
        recyclerview.adapter = adapter
    }

    private fun getComponentTypeList() = resources.getStringArray(R.array.build_component_list).toList()

    private fun setProcessorType() {
        processor_switch.setOnSwitchListener { position, _ ->
            when(position){
                0 -> {socket_switch_intel.visibility = VISIBLE; socket_switch_amd.visibility = GONE}
                1 -> {socket_switch_intel.visibility = GONE; socket_switch_amd.visibility = VISIBLE}
            }
        }
    }
}
