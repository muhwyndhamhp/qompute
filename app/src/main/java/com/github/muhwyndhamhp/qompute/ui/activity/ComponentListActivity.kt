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

class ComponentListActivity : AppCompatActivity() {

    private lateinit var viewModel: ComponentListViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: ComponentListAdapter? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component_list)

        progressDialog = indeterminateProgressDialog("Memuat data dari database...", "Loading")

        val factory = InjectorUtils.provideComponentListViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(ComponentListViewModel::class.java)
        getData()
        setSearchListener()
    }

    private fun setSearchListener() {
        et_cari_komponen.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                if(et_cari_komponen.text.toString() == "") viewModel.getData(intent.getStringExtra(CATEGORY_CODE))
                else viewModel.getDataFromSearch(intent.getStringExtra(CATEGORY_CODE), et_cari_komponen.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun prepareRecyclerView(components: List<Component>) {
        recyclerView = recycler_view_component_list
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = ComponentListAdapter(this, components)
        recyclerView.adapter = adapter
    }

    private fun getData() {
        progressDialog.show()
        viewModel.getData(intent.getStringExtra(CATEGORY_CODE))
        viewModel.componentList.observe(this, Observer {
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
}
