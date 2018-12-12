package com.github.muhwyndhamhp.qompute.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingAdapter
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.fragment_build_summary.*

class BuildSummaryFragment : Fragment() {

    companion object {
        fun newInstance() = BuildSummaryFragment
    }


    private lateinit var adapter: BuildingAdapter
    private lateinit var recyclerview: RecyclerView
    lateinit var viewModel: BuildingViewModel
    private var isFirstCpu = false
    private var isFirstSocket = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build_summary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (context as BuildingActivity).viewModel

        prepareComponentRecyclerView()
        setProcessorType()
    }

    private fun prepareComponentRecyclerView() {
        recyclerview = component_list_recycler_view
        recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = BuildingAdapter(context!!, this, viewModel)
        recyclerview.setItemViewCacheSize(20)
        recyclerview.isDrawingCacheEnabled = true
        recyclerview.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        recyclerview.adapter = adapter
    }

    private fun setProcessorType() {
        processor_switch.setOnSwitchListener { position, _ ->
            when (position) {
                0 -> {
                    socket_switch_intel.visibility = View.VISIBLE; socket_switch_amd.visibility = View.GONE
                    viewModel.cpuBrand.value = position
                    viewModel.build.value!!.cpuType = position
                    if (isFirstCpu) isFirstCpu = false
                    else {
                        viewModel.socketType.value = socket_switch_intel.selectedTab
                        viewModel.build.value!!.socketType = socket_switch_intel.selectedTab
                        viewModel.updateProcessorType()
                        adapter.clearProcessor()
                    }
                }
                1 -> {
                    socket_switch_intel.visibility = View.GONE; socket_switch_amd.visibility = View.VISIBLE
                    viewModel.cpuBrand.value = position
                    viewModel.build.value!!.cpuType = position
                    if (isFirstCpu) isFirstCpu = false
                    else {
                        viewModel.socketType.value = socket_switch_amd.selectedTab
                        viewModel.build.value!!.socketType = socket_switch_amd.selectedTab
                        viewModel.updateProcessorType()
                        adapter.clearProcessor()
                    }

                }
            }
        }
        socket_switch_intel.setOnSwitchListener { position, _ ->
            viewModel.cpuBrand.value = processor_switch.selectedTab
            viewModel.build.value!!.cpuType = processor_switch.selectedTab
            viewModel.socketType.value = position
            viewModel.build.value!!.socketType = position
            if (isFirstSocket) isFirstSocket = false
            else {
                viewModel.updateProcessorType()
                adapter.clearProcessor()
            }
        }
        socket_switch_amd.setOnSwitchListener { position, _ ->
            viewModel.cpuBrand.value = processor_switch.selectedTab
            viewModel.build.value!!.cpuType = processor_switch.selectedTab
            viewModel.socketType.value = position
            viewModel.build.value!!.socketType = position
            if (isFirstSocket) isFirstSocket = false
            else {
                viewModel.updateProcessorType()
                adapter.clearProcessor()
            }
        }

        if (viewModel.build.value!!.id == 0.toLong()) {
            processor_switch.selectedTab = 1
            Handler().postDelayed({
                processor_switch.selectedTab = 0
            }, 100)
        } else {
            isFirstCpu = true
            isFirstSocket = true
            processor_switch.selectedTab = viewModel.build.value!!.cpuType
            if(viewModel.build.value!!.cpuType == 0) socket_switch_intel.selectedTab = viewModel.build.value!!.socketType
            else socket_switch_amd.selectedTab = viewModel.build.value!!.socketType
        }
    }

    fun updateList(position: Int) {
        adapter.notifyItemChanged(position)
    }
}