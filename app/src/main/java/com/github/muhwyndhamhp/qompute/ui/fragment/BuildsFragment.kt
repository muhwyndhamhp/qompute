package com.github.muhwyndhamhp.qompute.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.ui.activity.MainActivity
import com.github.muhwyndhamhp.qompute.ui.adapter.BuildsAdapter
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.BuildsViewModel
import kotlinx.android.synthetic.main.fragment_build.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BuildsFragment : Fragment() {

    companion object {
        fun newInstance() = BuildsFragment()
    }

    lateinit var viewModel: BuildsViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: BuildsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = InjectorUtils.provideBuildsViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, factory).get(BuildsViewModel::class.java)

        view.bt_create.onClick {
            val intent = Intent(context, BuildingActivity::class.java)
            startActivity(intent)
        }

        viewModel.builds.observe(this, Observer {
            if (!::adapter.isInitialized) prepareRecyclerView(it)
            else {
                adapter.buildList = it
                adapter.notifyDataSetChanged()
                recyclerView.scheduleLayoutAnimation()
            }
            if(it.isEmpty()){
                view.imageView.visibility = View.VISIBLE
                view.tV_no_builds.visibility = View.VISIBLE
            } else {
                view.imageView.visibility = View.GONE
                view.tV_no_builds.visibility = View.GONE
            }
        })
    }

    private fun prepareRecyclerView(builds: List<Build>) {
        recyclerView = view!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        adapter = BuildsAdapter((context!! as MainActivity), builds)
        recyclerView.adapter = adapter
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