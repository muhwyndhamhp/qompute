package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.ComponentListActivity
import com.github.muhwyndhamhp.qompute.ui.activity.MainActivity
import com.github.muhwyndhamhp.qompute.utils.CATEGORY_CODE
import kotlinx.android.synthetic.main.item_browse.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BrowseAdapter(private val context: Context) : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryCode: String = ""

        fun bindView(category: String, context: Context) {

            val categories = context.resources.getStringArray(R.array.component_categories)
            when (category) {
                categories[0] -> setContent(R.drawable.ic_case, categories[0], itemView, context, 0)
                categories[1] -> setContent(R.drawable.ic_fan, categories[1], itemView, context, 1)
                categories[2] -> setContent(R.drawable.ic_hard_disk, categories[2], itemView, context, 2)
                categories[3] -> setContent(R.drawable.ic_keyboard, categories[3], itemView, context, 3)
                categories[4] -> setContent(R.drawable.ic_computer, categories[4], itemView, context, 4)
                categories[5] -> setContent(R.drawable.ic_ram, categories[5], itemView, context, 5)
                categories[6] -> setContent(R.drawable.ic_motherboard, categories[6], itemView, context, 6)
                categories[7] -> setContent(R.drawable.ic_optical, categories[7], itemView, context, 7)
                categories[8] -> setContent(R.drawable.ic_printer, categories[8], itemView, context, 8)
                categories[9] -> setContent(R.drawable.ic_cpu, categories[9], itemView, context, 9)
                categories[10] -> setContent(R.drawable.ic_supply, categories[10], itemView, context, 10)
                categories[11] -> setContent(R.drawable.ic_card_reader, categories[11], itemView, context, 11)
                categories[12] -> setContent(R.drawable.ic_speaker, categories[12], itemView, context, 12)
                categories[13] -> setContent(R.drawable.ic_graphic_card, categories[13], itemView, context, 13)
            }

            itemView.onClick {
                (context as MainActivity).showLoading("Memuat data...", "Loading")

//                val dialog = ProgressDialog(context)
//                dialog.setMessage("Memuat Data...")
//                dialog.show()
                val intent = Intent(context, ComponentListActivity::class.java)
                intent.putExtra(CATEGORY_CODE, categoryCode)
                context.startActivity(intent)
                context.dismissLoading()
            }
        }

        //        @SuppressLint("CheckResult")
        private fun setContent(
            categoryId: Int,
            categoryString: String,
            itemView: View,
            context: Context,
            address: Int
        ) {
            Glide.with(context).load(categoryId).into(itemView.iv_category)
            itemView.tv_category.text = categoryString
            categoryCode = context.resources.getStringArray(R.array.component_endpoint)[address]
        }
    }

    lateinit var categories: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_browse,
                    parent,
                    false
                )
        )

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: BrowseAdapter.ViewHolder, position: Int) =
        holder.bindView(categories[position], context)

}