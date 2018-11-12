package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildComponentSelectFragment
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildSummaryFragment

class BuildingPagerAdapter(
    val context: Context,
    private val tabNumber: Int,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> BuildSummaryFragment()
        else -> BuildComponentSelectFragment()
    }

    override fun getCount() = tabNumber

}