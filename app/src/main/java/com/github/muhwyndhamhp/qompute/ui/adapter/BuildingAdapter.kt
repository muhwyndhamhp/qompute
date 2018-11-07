package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import kotlinx.android.synthetic.main.item_build_component.view.*

class BuildingAdapter(private val context: Context, private val componentList: List<String>)
    : RecyclerView.Adapter<BuildingAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(context: Context, componentName: String) {
            itemView.tv_component_name_build.text = componentName
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_build_component,
                parent,
                false))

    override fun getItemCount() = componentList.size

    override fun onBindViewHolder(holder: BuildingAdapter.ViewHolder, position: Int) =
            holder.bindView(context, componentList[position])

}