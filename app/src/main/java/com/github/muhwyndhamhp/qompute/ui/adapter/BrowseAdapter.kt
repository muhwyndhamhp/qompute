package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.res.TypedArray
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
            categoryDrawable: Int,
            glide: RequestManager
        ) {
            itemView.tv_category.text = category
            glide.load(categoryDrawable).into(itemView.iv_category)
        }
    }

    lateinit var categories: List<String>
    lateinit var categoriesDrawable : TypedArray

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
        holder.bindView(categories[position], categoriesDrawable.getResourceId(position, 0), glide)

}