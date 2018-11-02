package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Component
import kotlinx.android.synthetic.main.item_component_list.view.*
import java.text.NumberFormat
import java.util.*

class ComponentListAdapter(private val context: Context, private var components: List<Component>): RecyclerView.Adapter<ComponentListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(component: Component) {
            itemView.tv_component_name.text = component.name
            itemView.tv_brand_name.text = component.brandDescription
            itemView.tv_price.text = getCurrency(component.price)
        }

        private fun getCurrency(price: String) = NumberFormat
            .getCurrencyInstance(Locale.forLanguageTag("IN"))
            .format(price.toInt())

    }

    fun setComponentList(componentList : List<Component>){
        components = componentList
        notifyDataSetChanged()
    }

    //RecyclerViewImplements
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_component_list,
                parent,
                false))
    override fun getItemCount(): Int = components.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindView(components[position])



}