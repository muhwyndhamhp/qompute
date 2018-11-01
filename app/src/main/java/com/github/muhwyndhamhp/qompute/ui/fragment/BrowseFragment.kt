package com.github.muhwyndhamhp.qompute.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.BrowseViewModel
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment: Fragment(){
    companion object {
        fun newInstance() = BrowseFragment()
    }

    private lateinit var viewModel: BrowseViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_browse, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = recyclerViewBrowse
        val factory = InjectorUtils.provideBrowseViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, factory).get(BrowseViewModel::class.java)

        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {

    }
}