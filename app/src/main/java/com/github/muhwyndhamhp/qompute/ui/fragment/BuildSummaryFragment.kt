package com.github.muhwyndhamhp.qompute.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildingAdapter
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.fragment_build_summary.*

class BuildSummaryFragment : Fragment() {

    private lateinit var rvAdapter: BuildingAdapter
    lateinit var viewModel: BuildingViewModel
    private var isFirstCpu = false
    private var isFirstSocket = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build_summary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (context as BuildingActivity).viewModel

        prepareComponentRecyclerView()
        prepareSwitch()
    }

    fun updateList(position: Int) {
        rvAdapter.notifyItemChanged(position)
    }

    private fun prepareComponentRecyclerView() {
        rvAdapter = BuildingAdapter(context!!, this, viewModel)
        component_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            adapter = rvAdapter
        }
    }

    private fun prepareSwitch() {
        setProcessorType()
        setSocketType()
        checkIfFirstTime()
    }

    private fun setProcessorType() {
        processor_switch.setOnSwitchListener { position, _ ->
            viewModel.setCpuBrand(position)
            when (position) {
                0 -> {
                    socket_switch_intel.visibility = View.VISIBLE; socket_switch_amd.visibility = View.GONE
                    if (isFirstCpu) isFirstCpu = false
                    else viewModel.setSocket(socket_switch_intel.selectedTab, isFirstCpu)

                }
                1 -> {
                    socket_switch_intel.visibility = View.GONE; socket_switch_amd.visibility = View.VISIBLE
                    if (isFirstCpu) isFirstCpu = false
                    else viewModel.setSocket(socket_switch_amd.selectedTab, isFirstCpu)

                }
            }
        }
    }

    private fun setSocketType() {
        socket_switch_intel.setOnSwitchListener { position, _ ->
            viewModel.apply {
                setCpuBrand(processor_switch.selectedTab)
                setSocket(position, true)
            }
            if (isFirstSocket) isFirstSocket = false
            else viewModel.setSocket(position, isFirstSocket)

        }
        socket_switch_amd.setOnSwitchListener { position, _ ->
            viewModel.apply {
                setCpuBrand(processor_switch.selectedTab)
                setSocket(position, true)
            }

            if (isFirstSocket) isFirstSocket = false
            else viewModel.setSocket(position, isFirstSocket)
        }
    }

    private fun checkIfFirstTime() {
        if (viewModel.build.value!!.id == 0.toLong()) {
            processor_switch.selectedTab = 1
            Handler().postDelayed({
                processor_switch.selectedTab = 0
            }, 100)
        } else {
            isFirstCpu = true
            isFirstSocket = true
            processor_switch.selectedTab = viewModel.build.value!!.cpuType
            when (viewModel.build.value!!.cpuType) {
                0 -> socket_switch_intel.selectedTab =
                    viewModel.build.value!!.socketType
                1 -> socket_switch_amd.selectedTab = viewModel.build.value!!.socketType
            }
        }
    }


}