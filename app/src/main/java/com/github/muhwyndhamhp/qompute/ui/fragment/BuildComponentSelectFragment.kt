package com.github.muhwyndhamhp.qompute.ui.fragment

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.ComponentListAdapter
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_2
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.activity_component_list.*
import kotlinx.android.synthetic.main.fragment_build_component_selection.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.sdk27.coroutines.onClick

class BuildComponentSelectFragment : Fragment() {

    companion object {
        fun newInstance() = BuildComponentSelectFragment
    }

    private lateinit var recyclerView: RecyclerView
    private var adapter: ComponentListAdapter? = null
//    private lateinit var progressDialog: ProgressDialog
    private var isAscendingPrice = true
    private var isAscendingName = true

    private lateinit var viewModel: BuildingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build_component_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = (context as BuildingActivity).getViewModelFactory()
        viewModel = activity?.run {
            ViewModelProviders.of(this, factory).get(BuildingViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

//        progressDialog = context!!.indeterminateProgressDialog("Memuat data dari database...", "Loading")
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
            recyclerView.smoothScrollToPosition(0)
        }

    }

    private fun getComponentName(value: Int): String {
        return when (value) {
            101 -> context!!.resources.getStringArray(R.array.component_endpoint)[1]
            102 -> context!!.resources.getStringArray(R.array.component_endpoint)[2]
            else -> context!!.resources.getStringArray(R.array.component_endpoint)[value]
        }
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun hideSoftKey() {
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) imm.hideSoftInputFromWindow(activity!!.currentFocus.windowToken, 0)
    }

    private fun getData() {
        viewModel.componentPosition.observe(this, Observer { componentPosition ->
            if(componentPosition in 0..15)
            {
//                progressDialog.show()
                viewModel.getData(getComponentName(componentPosition))
            }
        })

        viewModel.componentListA.observe(this, Observer {
            if (adapter == null) prepareRecyclerView(it)
            else adapter!!.setComponentList(it)
//            when (progressDialog.isShowing) {
//                true -> progressDialog.dismiss()
//            }
        })

        viewModel.exceptionList.observe(this, Observer {
            if (it.size != 0) {
                when {
                    it[ERROR_CODE_FAILED_TO_FETCH_PART_1].message != "" -> {
//                        when (progressDialog.isShowing) {
//                            true -> progressDialog.dismiss()
//                        }
                        context!!.alert { it[ERROR_CODE_FAILED_TO_FETCH_PART_1] }
                    }
                    it[ERROR_CODE_FAILED_TO_FETCH_PART_2].message != "" -> {
//                        when (progressDialog.isShowing) {
//                            true -> progressDialog.dismiss()
//                        }
                        context!!.alert { it[ERROR_CODE_FAILED_TO_FETCH_PART_2] }
                    }
                }

            }
        })
    }

    private fun prepareRecyclerView(components: List<Component>) {
        recyclerView = recycler_view_component_list
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = ComponentListAdapter(context!!, components)
        adapter!!.setCategoryCode(getComponentName(viewModel.componentPosition.value!!))
        recyclerView.adapter = adapter
    }
}