package com.github.muhwyndhamhp.qompute.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.MainActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.BrowseAdapter
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment() : Fragment() {
    companion object {
        fun newInstance() = BrowseFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BrowseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_browse, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = recyclerViewBrowse
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = BrowseAdapter((context!! as MainActivity), Glide.with(context!!))
        recyclerView.adapter = adapter
        adapter.categories = context!!.resources.getStringArray(R.array.component_categories).toList()
        recyclerView.scheduleLayoutAnimation()
    }

    override fun onPause() {
        super.onPause()
        recyclerView.adapter = null

    }

    override fun onResume() {
        super.onResume()
        if(::adapter.isInitialized) recyclerView.adapter = adapter
    }
}