package com.github.muhwyndhamhp.qompute.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.github.muhwyndhamhp.qompute.R
import kotlinx.android.synthetic.main.activity_building.*

class BuildingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building)

        processor_switch.setOnSwitchListener { position, _ ->
            when(position){
                0 -> {socket_switch_intel.visibility = VISIBLE; socket_switch_amd.visibility = GONE}
                1 -> {socket_switch_intel.visibility = GONE; socket_switch_amd.visibility = VISIBLE}
            }
        }

    }
}
