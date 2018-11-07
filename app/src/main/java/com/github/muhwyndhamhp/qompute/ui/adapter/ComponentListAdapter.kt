package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.ui.activity.ComponentDetailActivity
import com.github.muhwyndhamhp.qompute.ui.activity.ComponentListActivity
import com.github.muhwyndhamhp.qompute.utils.CATEGORY_CODE
import com.github.muhwyndhamhp.qompute.utils.COMPONENT_CODE
import kotlinx.android.synthetic.main.item_component_list.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat
import java.util.*

class ComponentListAdapter(private val context: Context, private var components: List<Component>)
    : RecyclerView.Adapter<ComponentListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(
            component: Component,
            context: Context,
            categoryCode: String
        ) {
            itemView.tv_component_name.text = component.name
            itemView.tv_brand_name.text = component.brandDescription
            itemView.tv_price.text = getCurrency(component.price)
            itemView.tv_subcategory_name.text = component.subcategoryDescription

            itemView.onClick {
                (context as ComponentListActivity).showLoading("Memuat data...", "Loading")
                val intent = Intent(context, ComponentDetailActivity::class.java)
                intent.putExtra(COMPONENT_CODE, component)
                intent.putExtra(CATEGORY_CODE, categoryCode)
                context.startActivity(intent)
                context.dismissLoading()
            }
        }

        private fun getCurrency(price: String) = NumberFormat
            .getCurrencyInstance(Locale("in", "ID"))
            .format(price.toLong())

    }

    private lateinit var categoryCode: String

    fun setComponentList(componentList : List<Component>){
        components = componentList
        notifyDataSetChanged()
    }
    fun setCategoryCode(a: String) {
        categoryCode = a
    }

    //RecyclerViewImplements
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_component_list,
                parent,
                false))
    override fun getItemCount(): Int = components.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindView(components[position], context, categoryCode)



}