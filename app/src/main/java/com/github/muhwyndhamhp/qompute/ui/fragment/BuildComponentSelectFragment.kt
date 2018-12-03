package com.github.muhwyndhamhp.qompute.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.ComponentSelectionAdapter
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_2
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.activity_component_list.*
import kotlinx.android.synthetic.main.fragment_build_component_selection.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick

class BuildComponentSelectFragment : Fragment() {

    companion object {
        fun newInstance() = BuildComponentSelectFragment
    }

    private lateinit var recyclerView: RecyclerView
    private var adapter: ComponentSelectionAdapter? = null
    private var isAscendingPrice = true
    private var isAscendingName = true

    private lateinit var viewModel: BuildingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build_component_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (context as BuildingActivity).viewModel
        view.swipe_refresh_layout.setOnRefreshListener { view.swipe_refresh_layout.isRefreshing = false }
        view.swipe_refresh_layout.setColorSchemeColors(context!!.resources.getColor(R.color.colorAccent))
        getData()
        sortButtonClickListener()
    }

    private fun sortButtonClickListener() {
        view!!.bt_by_name.onClick {
            if (isAscendingName) {
                viewModel.componentListA.value =
                        viewModel.componentListA.value!!.sortedWith(compareBy { component -> component.name })
                isAscendingName = false
            } else {
                viewModel.componentListA.value =
                        viewModel.componentListA.value!!.sortedWith(compareByDescending { component -> component.name })
                isAscendingName = true
            }
        }
        view!!.bt_by_price.onClick {
            if (isAscendingPrice) {
                viewModel.componentListA.value =
                        viewModel.componentListA.value!!.sortedWith(compareBy { component -> component.price.toLong() })
                isAscendingPrice = false
            } else {
                viewModel.componentListA.value =
                        viewModel.componentListA.value!!.sortedWith(compareByDescending { component -> component.price.toLong() })
                isAscendingPrice = true
            }
        }
        view!!.bt_filter.onClick {

            //TODO the logic still flawed, don't forget to return the pre-filter value back first before doing next filtering
            when {
                view!!.et_min_price.text.toString() != "" && view!!.et_max_price.text.toString() != "" -> {

                    val searchQuery = if (view!!.et_cari_komponen.text.toString() == "") " "
                    else view!!.et_cari_komponen.text.toString()

                    viewModel.getDataFromSearchFilteredMinMax(
                        getComponentName(viewModel.componentPosition.value!!),
                        searchQuery,
                        view!!.et_min_price.text.toString().toLong(),
                        view!!.et_max_price.text.toString().toLong()
                    )
                }

                view!!.et_min_price.text.toString() == "" && view!!.et_max_price.text.toString() != "" ->
                    viewModel.getDataFromSearchFilteredMax(
                        getComponentName(viewModel.componentPosition.value!!),
                        view!!.et_cari_komponen.text.toString(),
                        view!!.et_max_price.text.toString().toLong()
                    )

                view!!.et_min_price.text.toString() != "" && view!!.et_max_price.text.toString() == "" ->
                    viewModel.getDataFromSearchFilteredMin(
                        getComponentName(viewModel.componentPosition.value!!),
                        view!!.et_cari_komponen.text.toString(),
                        view!!.et_min_price.text.toString().toLong()
                    )

                else ->
                    viewModel.getDataFromSearch(
                        getComponentName(viewModel.componentPosition.value!!),
                        view!!.et_cari_komponen.text.toString()
                    )
            }

            hideSoftKey()
        }

    }

    private fun getComponentName(value: Int): String {
        return viewModel.getComponentName(
            value,
            context!!.resources.getStringArray(R.array.component_endpoint),
            context!!.resources.getStringArray(R.array.sub_component_endpoint),
            context!!.resources.getStringArray(R.array.socket_intel),
            context!!.resources.getStringArray(R.array.socket_amd)
        )
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun hideSoftKey() {
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) imm.hideSoftInputFromWindow(activity!!.currentFocus.windowToken, 0)
    }

    private fun getData() {
        viewModel.componentPosition.observe(this, Observer { componentPosition ->
            if (componentPosition != 99) {
                view!!.swipe_refresh_layout.isRefreshing = true
                if (adapter != null) adapter!!.setComponentList(listOf())
                Handler().postDelayed({ viewModel.getData(getComponentName(componentPosition)) }, 500)
            }
        })

        viewModel.componentListA.observe(this, Observer {
            if (adapter == null) prepareRecyclerView(it)
            else {
                adapter!!.setComponentList(it)
                recyclerView.scheduleLayoutAnimation()
            }
            Handler().postDelayed({
                if (view!!.swipe_refresh_layout.isRefreshing) view!!.swipe_refresh_layout.isRefreshing = false
            }, 1000)
            recyclerView.smoothScrollToPosition(0)
        })

        viewModel.exceptionList.observe(this, Observer {
            if (it.size != 0) {
                when {
                    it[ERROR_CODE_FAILED_TO_FETCH_PART_1].message != "" -> {
                        context!!.alert { it[ERROR_CODE_FAILED_TO_FETCH_PART_1] }
                    }
                    it[ERROR_CODE_FAILED_TO_FETCH_PART_2].message != "" -> {
                        context!!.alert { it[ERROR_CODE_FAILED_TO_FETCH_PART_2] }
                    }
                }

            }
        })
    }

    private fun prepareRecyclerView(components: List<Component>) {
        recyclerView = recycler_view_component_list
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = ComponentSelectionAdapter(context!!, components)
        adapter!!.setCategoryCode(getComponentName(viewModel.componentPosition.value!!))
        recyclerView.adapter = adapter
        recyclerView.scheduleLayoutAnimation()
    }

    fun addComponent(component: Component) {
        viewModel.addComponent(component)
        (context as BuildingActivity).changeFragment(0, viewModel.componentInBuildPosition.value!!)
    }

}