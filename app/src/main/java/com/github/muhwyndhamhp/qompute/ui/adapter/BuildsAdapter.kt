package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.ui.activity.BuildPreviewActivity
import com.github.muhwyndhamhp.qompute.utils.BUILD_DATA_CODE
import kotlinx.android.synthetic.main.item_build_preview.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat
import java.util.*

class BuildsAdapter(private val context: Context, var buildList: List<Build>) :
    RecyclerView.Adapter<BuildsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(build: Build, context: Context) {
            itemView.apply {
                tv_build_name.text = if (build.name != "") build.name else "Rakitan Baru"
                tv_price_total.text = NumberFormat
                    .getCurrencyInstance(Locale("in", "ID"))
                    .format(build.totalPrice)
                onClick {
                    val intent = Intent(context, BuildPreviewActivity::class.java)
                    intent.putExtra(BUILD_DATA_CODE, build)
                    context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildsAdapter.ViewHolder =
        BuildsAdapter.ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_build_preview,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = buildList.size
    override fun onBindViewHolder(holder: BuildsAdapter.ViewHolder, position: Int) =
        holder.bindView(buildList[position], context)


}