package com.github.muhwyndhamhp.qompute.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import kotlinx.android.synthetic.main.item_buiod_component_view.view.*
import java.text.NumberFormat
import java.util.*

class BuildPreviewAdapter(val context: Context, val build: Build) :
    RecyclerView.Adapter<BuildPreviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(position: Int, build: Build, context: Context) {

            itemView.apply {
                tv_component_item_name.text =
                        if (build.componentName!![position] == "") "Rakitan Baru" else build.componentName!![position]
                tv_component_item_total_price.text = "Harga total komponen : \n" + NumberFormat
                    .getCurrencyInstance(Locale("in", "ID"))
                    .format(countItemTotalPrice(build, position))
                tv_item_count.text = "${build.componentCount!![position]} Item"
            }

        }

        private fun countItemTotalPrice(build: Build, position: Int): Long {
            return build.componentPrice!![position] * build.componentCount!![position]
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (build.componentIds!![position] == "") 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildPreviewAdapter.ViewHolder =
        BuildPreviewAdapter.ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_buiod_component_view,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = build.componentCount?.size ?: 0

    override fun onBindViewHolder(holder: BuildPreviewAdapter.ViewHolder, position: Int) =
        if (holder.itemViewType == 0) {
            val layoutParam = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParam.height = 0
            holder.itemView.layoutParams = layoutParam
        } else {
            holder.bindView(position, build, context)
        }

}