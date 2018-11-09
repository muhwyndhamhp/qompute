package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.BuildingActivity
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.item_build_component.view.*

class BuildingAdapter(private val context: Context, private var componentList: List<String>, private val viewModel: BuildingViewModel)
    : RecyclerView.Adapter<BuildingAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(
            context: Context,
            componentLinker: String,
            viewModel: BuildingViewModel,
            componentId: Int
        ) {
            itemView.tv_component_name_build.text = componentLinker

            itemView.spinner_item_count.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (itemView.tv_component_name_build.text != componentLinker) viewModel.changeComponentCount(position, componentId)
                }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_build_component,
                parent,
                false))

    override fun getItemCount() = componentList.size

    override fun onBindViewHolder(holder: BuildingAdapter.ViewHolder, position: Int)
    {
        holder.bindView(context, componentList[position], viewModel, position)
    }

}