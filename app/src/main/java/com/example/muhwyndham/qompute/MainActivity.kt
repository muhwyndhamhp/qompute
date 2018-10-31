package com.example.muhwyndham.qompute

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.utils.ERROR_CODE_FAILED_TO_FETCH
import com.example.muhwyndham.qompute.utils.InjectorUtils
import com.example.muhwyndham.qompute.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    val list = ArrayList<String>()
    private var adapterList: ArrayAdapter<String>? = null

    private lateinit var mProgressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = InjectorUtils.provideMainViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        mProgressDialog = indeterminateProgressDialog("Loading Data From Server", "Loading")
        prepareListView()
        loadData()
    }

    private fun loadData() {
        mProgressDialog.show()
        viewModel.LoadAllComponents()
    }

    private fun prepareListView() {
        viewModel.componentList.observe(this, Observer { componentList ->
            if(componentList != null){
                insertComponentToListAndInflate(componentList)
            }
        })
        viewModel.exceptionList.observe(this, Observer { exceptions ->
            if(exceptions != null)
            if(exceptions[ERROR_CODE_FAILED_TO_FETCH] != ""){
                mProgressDialog.dismiss()
                toast(exceptions[ERROR_CODE_FAILED_TO_FETCH])
                viewModel.exceptionList.value!![ERROR_CODE_FAILED_TO_FETCH] = ""
            }
        })
    }

    private fun insertComponentToListAndInflate(componentList: List<Component>) {
        for(component in componentList){
            list.add(component.name)
        }
        listview.adapter = adapterList ?: ArrayAdapter(this, android.R.layout.simple_list_item_1, list).also { adapterList = it }
        mProgressDialog.dismiss()
    }
}
