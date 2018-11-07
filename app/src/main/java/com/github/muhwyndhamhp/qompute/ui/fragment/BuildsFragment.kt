package com.github.muhwyndhamhp.qompute.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import kotlinx.android.synthetic.main.fragment_build.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BuildsFragment : Fragment(){

    companion object {
        fun newInstance() = BuildsFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_build, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.bt_create.onClick {
            val intent = Intent(context, BuildingActivity::class.java)
            startActivity(intent)
        }
    }
}