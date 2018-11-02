package com.github.muhwyndhamhp.qompute.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.ComponentListActivity
import kotlinx.android.synthetic.main.item_browse.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BrowseAdapter(private val context: Context) : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(category: String, context: Context) {
            val categories = context.resources.getStringArray(R.array.component_categories)
            when (category) {
                categories[0] -> setContent(R.drawable.ic_case, categories[0], itemView, context)
                categories[1] -> setContent(R.drawable.ic_fan, categories[1], itemView, context)
                categories[2] -> setContent(R.drawable.ic_hard_disk, categories[2], itemView, context)
                categories[3] -> setContent(R.drawable.ic_keyboard, categories[3], itemView, context)
                categories[4] -> setContent(R.drawable.ic_computer, categories[4], itemView, context)
                categories[5] -> setContent(R.drawable.ic_ram, categories[5], itemView, context)
                categories[6] -> setContent(R.drawable.ic_motherboard, categories[6], itemView, context)
                categories[7] -> setContent(R.drawable.ic_optical, categories[7], itemView, context)
                categories[8] -> setContent(R.drawable.ic_printer, categories[8], itemView, context)
                categories[9] -> setContent(R.drawable.ic_cpu, categories[9], itemView, context)
                categories[10] -> setContent(R.drawable.ic_supply, categories[10], itemView, context)
                categories[11] -> setContent(R.drawable.ic_card_reader, categories[11], itemView, context)
                categories[12] -> setContent(R.drawable.ic_speaker, categories[12], itemView, context)
                categories[13] -> setContent(R.drawable.ic_ssd, categories[13], itemView, context)
                categories[14] -> setContent(R.drawable.ic_graphic_card, categories[14], itemView, context)
            }

            itemView.onClick {
                val intent = Intent(context, ComponentListActivity::class.java)
                context.startActivity(intent)
            }
        }

        @SuppressLint("CheckResult")
        private fun setContent(categoryId: Int, categoryString: String, itemView: View, context: Context) {
            Glide.with(context).load(categoryId).into(itemView.iv_category)
            itemView.tv_category.text = categoryString
        }
    }

    lateinit var categories: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseAdapter.ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_browse,
                parent,
                false))

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: BrowseAdapter.ViewHolder, position: Int) = holder.bindView(categories[position], context)

}