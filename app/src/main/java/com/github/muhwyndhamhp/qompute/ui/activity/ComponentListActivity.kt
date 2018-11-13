package com.github.muhwyndhamhp.qompute.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.ui.adapter.ComponentListAdapter
import com.github.muhwyndhamhp.qompute.utils.CATEGORY_CODE
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_1
import com.github.muhwyndhamhp.qompute.utils.ERROR_CODE_FAILED_TO_FETCH_PART_2
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.ComponentListViewModel
import kotlinx.android.synthetic.main.activity_component_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*
import kotlin.concurrent.schedule

class ComponentListActivity : AppCompatActivity() {

    private lateinit var viewModel: ComponentListViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: ComponentListAdapter? = null
    private lateinit var progressDialog: ProgressDialog
    private var isAscendingPrice = true
    private var isAscendingName = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component_list)

        progressDialog = indeterminateProgressDialog("Memuat data dari database...", "Loading")

        setSupportActionBar(toolbar_component_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_component_list.setNavigationOnClickListener { onBackPressed() }

        val factory = InjectorUtils.provideComponentListViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(ComponentListViewModel::class.java)
        getData()
        sortButtonClickListener()
    }


    private fun prepareRecyclerView(components: List<Component>) {
        recyclerView = recycler_view_component_list
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = ComponentListAdapter(this, components)
        adapter!!.setCategoryCode(intent.getStringExtra(CATEGORY_CODE))
        recyclerView.adapter = adapter
    }

    private fun getData() {
        progressDialog.show()
        viewModel.getData(intent.getStringExtra(CATEGORY_CODE))
        viewModel.componentListA.observe(this, Observer {
            if (adapter == null) prepareRecyclerView(it)
            else adapter!!.setComponentList(it)
            when (progressDialog.isShowing) {
                true -> progressDialog.dismiss()
            }
        })

        viewModel.exceptionList.observe(this, Observer {
            if (it.size != 0) {
                when {
                    it[ERROR_CODE_FAILED_TO_FETCH_PART_1].message != "" -> {
                        when (progressDialog.isShowing) {
                            true -> progressDialog.dismiss()
                        }
                        alert { it[ERROR_CODE_FAILED_TO_FETCH_PART_1] }
                    }
                    it[ERROR_CODE_FAILED_TO_FETCH_PART_2].message != "" -> {
                        when (progressDialog.isShowing) {
                            true -> progressDialog.dismiss()
                        }
                        alert { it[ERROR_CODE_FAILED_TO_FETCH_PART_2] }
                    }
                }

            }
        })
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

    private fun sortButtonClickListener() {
        bt_by_name.onClick {
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
        bt_by_price.onClick {
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
        bt_filter.onClick {

            //TODO the logic still flawed, don't forget to return the pre-filter value back first before doing next filtering
            when {
                et_min_price.text.toString() != "" && et_max_price.text.toString() != "" ->
                {

                    val searchQuery = if(et_cari_komponen.text.toString() == "") " "
                    else et_cari_komponen.text.toString()

                    viewModel.getDataFromSearchFilteredMinMax(
                        intent.getStringExtra(CATEGORY_CODE),
                        searchQuery,
                        et_min_price.text.toString().toLong(),
                        et_max_price.text.toString().toLong()
                    )
                }

                et_min_price.text.toString() == "" && et_max_price.text.toString() != "" ->
                    viewModel.getDataFromSearchFilteredMax(
                        intent.getStringExtra(CATEGORY_CODE),
                        et_cari_komponen.text.toString(),
                        et_max_price.text.toString().toLong()
                    )

                et_min_price.text.toString() != "" && et_max_price.text.toString() == "" ->
                    viewModel.getDataFromSearchFilteredMin(
                        intent.getStringExtra(CATEGORY_CODE),
                        et_cari_komponen.text.toString(),
                        et_min_price.text.toString().toLong()
                    )

                else ->
                    viewModel.getDataFromSearch(
                        intent.getStringExtra(CATEGORY_CODE),
                        et_cari_komponen.text.toString()
                    )
            }
        }
    }
}
