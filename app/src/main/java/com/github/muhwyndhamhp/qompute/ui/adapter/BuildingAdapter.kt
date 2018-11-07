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
import kotlinx.android.synthetic.main.item_build_component.view.*

class BuildingAdapter(private val context: Context, private var componentList: List<ComponentLinker>, private val viewModel: ViewModel)
    : RecyclerView.Adapter<BuildingAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(
            context: Context,
            componentLinker: ComponentLinker,
            viewModel: ViewModel,
            i: Int
        ) {
            itemView.tv_component_name_build.text = componentLinker.name
            itemView.spinner_item_count.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    (context as BuildingActivity).setComponentCount(position+1, i)
                }

            }
        }


    }

    class ComponentLinker(
        var id: String?,
        val name: String,
        var itemCount: Int
    )


    fun updateData(componentLinkers: MutableList<ComponentLinker>) {
        componentList = componentLinkers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_build_component,
                parent,
                false))

    override fun getItemCount() = componentList.size

    override fun onBindViewHolder(holder: BuildingAdapter.ViewHolder, position: Int) =
            holder.bindView(context, componentList[position], viewModel, position)

}