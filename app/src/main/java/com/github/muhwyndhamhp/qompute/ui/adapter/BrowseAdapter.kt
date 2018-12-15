package com.github.muhwyndhamhp.qompute.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.utils.MainRecyclerViewCommunicator
import kotlinx.android.synthetic.main.item_browse.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BrowseAdapter(
    private val mainInterface: MainRecyclerViewCommunicator,
    private val glide: RequestManager
) : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(
            category: String,
            glide: RequestManager
        ) {
            itemView.tv_category.text = category
            when (adapterPosition) {
                0 -> glide.load(R.drawable.ic_case).into(itemView.iv_category)
                1 -> glide.load(R.drawable.ic_fan).into(itemView.iv_category)
                2 -> glide.load(R.drawable.ic_hard_disk).into(itemView.iv_category)
                3 -> glide.load(R.drawable.ic_keyboard).into(itemView.iv_category)
                4 -> glide.load(R.drawable.ic_computer).into(itemView.iv_category)
                5 -> glide.load(R.drawable.ic_ram).into(itemView.iv_category)
                6 -> glide.load(R.drawable.ic_motherboard).into(itemView.iv_category)
                7 -> glide.load(R.drawable.ic_optical).into(itemView.iv_category)
                8 -> glide.load(R.drawable.ic_printer).into(itemView.iv_category)
                9 -> glide.load(R.drawable.ic_cpu).into(itemView.iv_category)
                10 -> glide.load(R.drawable.ic_supply).into(itemView.iv_category)
                11 -> glide.load(R.drawable.ic_card_reader).into(itemView.iv_category)
                12 -> glide.load(R.drawable.ic_speaker).into(itemView.iv_category)
                13 -> glide.load(R.drawable.ic_graphic_card).into(itemView.iv_category)
            }
        }
    }

    lateinit var categories: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseAdapter.ViewHolder {
        val vh = ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_browse,
                    parent,
                    false
                )
        )
        vh.apply {
            itemView.onClick {
                mainInterface.showLoading("Memuat data...", "Loading")
                mainInterface.startComponentListActivity(mainInterface.getComponentEndpointArray()[vh.adapterPosition])
                mainInterface.dismissLoading()
            }
        }
        return vh
    }


    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: BrowseAdapter.ViewHolder, position: Int) =
        holder.bindView(categories[position], glide)

}