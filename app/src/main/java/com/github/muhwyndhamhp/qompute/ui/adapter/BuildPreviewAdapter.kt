package com.github.muhwyndhamhp.qompute.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.utils.BASEURL1
import com.github.muhwyndhamhp.qompute.utils.BASEURL2
import com.github.muhwyndhamhp.qompute.utils.BASEURL3
import kotlinx.android.synthetic.main.activity_component_detail.*
import kotlinx.android.synthetic.main.item_buiod_component_view.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
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
                tv_component_item_total_price.text = NumberFormat
                    .getCurrencyInstance(Locale("in", "ID"))
                    .format(countItemTotalPrice(build, position))
                tv_item_count.text = "${build.componentCount!![position]} Item"
                bt_buy_bukalapak_affiliate.onClick {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getAffiliateLink(build.componentName!![position]))
                    (context as Activity).startActivity(intent)
                }
            }

        }

        private fun countItemTotalPrice(build: Build, position: Int): Long {
            return build.componentPrice!![position] * build.componentCount!![position]
        }

        fun getAffiliateLink(string: String): String? {
            return buildLink("", formatText(string))

        }

        private fun formatText(name: String): String {
            val b  = name.replace("(", "%2528")
            val c = b.replace(")", "%2529")
            val d = c.split(" ").map { it.trim() }
            var e = String()
            for(i in d.indices){
                if(i >= 7){
                    break
                }
                e += "${d[i]}%2B"
            }
            return e
        }

        private fun buildLink(s: String, replace: String): String? {
            return "$BASEURL1$s$BASEURL2$replace$BASEURL3"
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