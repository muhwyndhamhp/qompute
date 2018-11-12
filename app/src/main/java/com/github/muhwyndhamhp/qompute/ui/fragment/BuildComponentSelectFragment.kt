package com.github.muhwyndhamhp.qompute.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import org.jetbrains.anko.toast

class BuildComponentSelectFragment : Fragment() {

    companion object {
        fun newInstance() = BuildComponentSelectFragment
    }

    private lateinit var viewModel: BuildingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build_component_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = (context as BuildingActivity).getViewModelFactory()
        viewModel = activity?.run {
            ViewModelProviders.of(this, factory).get(BuildingViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.componentPosition.observe(this, Observer {
            if (it != null) {
                context!!.toast("ComponentId is : $it")
                Handler().postDelayed({
                    (context as BuildingActivity).changeFragment(0)
                }, 1000)
            }
        })

    }
}