package com.github.muhwyndhamhp.qompute.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingAdapter
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.activity_building.*
import kotlinx.android.synthetic.main.fragment_build_summary.*

class BuildSummaryFragment(): Fragment(){

    companion object {
        fun newInstance()= BuildSummaryFragment
    }


    private lateinit var adapter: BuildingAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var viewModel: BuildingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build_summary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = (context as BuildingActivity).getViewModelFactory()
        viewModel = activity?.run {
            ViewModelProviders.of(this, factory).get(BuildingViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.initiateBuildObject((context as BuildingActivity).getBuildObjectIntent())
        setProcessorType()
        prepareComponentRecyclerView()
        processor_switch.selectedTab = 1
        Handler().postDelayed({
            processor_switch.selectedTab = 0
        }, 100)
    }

    private fun prepareComponentRecyclerView() {
        recyclerview = component_list_recycler_view
        recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = BuildingAdapter(context!!, this, viewModel)
        recyclerview.setItemViewCacheSize(20)
        recyclerview.adapter = adapter
    }

    private fun setProcessorType() {
        processor_switch.setOnSwitchListener { position, _ ->
            when (position) {
                0 -> {
                    socket_switch_intel.visibility = View.VISIBLE; socket_switch_amd.visibility = View.GONE
                    viewModel.updateProcessorType()
                    adapter.clearProcessor()
                }
                1 -> {
                    socket_switch_intel.visibility = View.GONE; socket_switch_amd.visibility = View.VISIBLE
                    viewModel.updateProcessorType()
                    adapter.clearProcessor()
                }
            }
        }
        socket_switch_intel.setOnSwitchListener { _, _ ->
            viewModel.updateProcessorType()
            adapter.clearProcessor()
        }
        socket_switch_amd.setOnSwitchListener { _, _ ->
            viewModel.updateProcessorType()
            adapter.clearProcessor()
        }
    }
}