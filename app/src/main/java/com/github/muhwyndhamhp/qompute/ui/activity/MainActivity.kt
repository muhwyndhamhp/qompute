package com.github.muhwyndhamhp.qompute.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.fragment.BrowseFragment
import com.github.muhwyndhamhp.qompute.ui.fragment.BuildsFragment
import com.github.muhwyndhamhp.qompute.ui.fragment.ProfileFragment
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation(bottomNavigationView)

        val factory = InjectorUtils.provideMainViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.fragmentPosition.observe(this, Observer { replaceFragment(selectFragment(it)) })
    }

    private fun replaceFragment(selectFragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, selectFragment)
        ft.commit()
    }

    private fun selectFragment(it: Int): Fragment {
        return when(it){
            0 -> BuildsFragment.newInstance()
            1 -> BrowseFragment.newInstance()
            2 -> ProfileFragment.newInstance()
            else -> BuildsFragment.newInstance()
        }

    }

    private fun setupBottomNavigation(bottomNavigationView: AHBottomNavigation) {
        val context = this
        val ahBottomNavigationAdapter = AHBottomNavigationAdapter(this, R.menu.menu_main)
        ahBottomNavigationAdapter.setupWithBottomNavigation(bottomNavigationView)
        bottomNavigationView.apply {
            titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
            accentColor = ContextCompat.getColor(context, R.color.colorAccent)
            inactiveColor = ContextCompat.getColor(context, R.color.defaultUnselected)
            defaultBackgroundColor = ContextCompat.getColor(context, R.color.white)
            setOnTabSelectedListener { position, _ -> viewModel.setFragmentPosition(position)
                true
            }
        }
    }
}
