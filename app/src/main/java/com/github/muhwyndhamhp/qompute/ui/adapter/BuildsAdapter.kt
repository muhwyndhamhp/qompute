package com.github.muhwyndhamhp.qompute.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.utils.MainRecyclerViewCommunicator
import kotlinx.android.synthetic.main.item_build_preview.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat
import java.util.*

class BuildsAdapter(private val mainInterface: MainRecyclerViewCommunicator, var buildList: List<Build>) :
    RecyclerView.Adapter<BuildsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(build: Build) {
            itemView.apply {
                tv_build_name.text = if (build.name != "") build.name else "Rakitan Baru"
                tv_price_total.text = NumberFormat
                    .getCurrencyInstance(Locale("in", "ID"))
                    .format(build.totalPrice)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildsAdapter.ViewHolder {
        val vh = ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_build_preview,
                    parent,
                    false
                )
        )

        vh.apply {
            itemView.onClick {
                mainInterface.startBuildPreviewActivity(buildList[vh.adapterPosition])
            }
        }
        return vh
    }


    override fun getItemCount(): Int = buildList.size
    override fun onBindViewHolder(holder: BuildsAdapter.ViewHolder, position: Int) =
        holder.bindView(buildList[position])


}