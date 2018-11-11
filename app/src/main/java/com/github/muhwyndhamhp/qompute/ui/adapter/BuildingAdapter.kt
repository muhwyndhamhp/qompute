package com.github.muhwyndhamhp.qompute.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.ui.activity.ComponentSelectionActivity
import com.github.muhwyndhamhp.qompute.utils.BUILD_ID_DB
import com.github.muhwyndhamhp.qompute.utils.CATEGORY_TYPE_CODE
import com.github.muhwyndhamhp.qompute.utils.CPU_BRAND_ID
import com.github.muhwyndhamhp.qompute.utils.SOCKET_ID
import com.github.muhwyndhamhp.qompute.viewmodel.BuildingViewModel
import kotlinx.android.synthetic.main.item_build_component.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BuildingAdapter(
    private val context: Context,
    private var componentList: List<String>,
    private val viewModel: BuildingViewModel
) : RecyclerView.Adapter<BuildingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var componentListPosition: Int? = 0

        fun bindView(
            context: Context,
            componentLinker: String,
            viewModel: BuildingViewModel,
            componentListPosition: Int
        ) {
            this.componentListPosition = componentListPosition
            itemView.tv_component_name_build.text = componentLinker
//            itemView.spinner_item_count.setSelection((viewModel.build.value?.componentCount?.get(componentListPosition) ?: 1)-1)

            itemView.spinner_item_count.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.changeComponentCount(
                        position + 1,
                        componentListPosition
                    )
                }

            }

            itemView.tv_component_name_build.onClick {
                startComponentSelectionActivity(
                    context,
                    componentListPosition,
                    viewModel
                )
            }
            itemView.iv_delete_component.onClick {
                viewModel.deleteComponent(componentListPosition)
//                itemView.spinner_item_count.setSelection(0)
            }
        }

        private fun startComponentSelectionActivity(context: Context, componentId: Int, viewModel: BuildingViewModel) {
            val intent = Intent(context, ComponentSelectionActivity::class.java)
            when (componentListPosition) {
                0 or 1 or 8 -> insertIntentExtra(intent, viewModel)
            }
            intent.putExtra(CATEGORY_TYPE_CODE, componentId)
            intent.putExtra(BUILD_ID_DB, viewModel.build.value!!.id)
            context.startActivity(intent)
        }

        private fun insertIntentExtra(
            intent: Intent,
            viewModel: BuildingViewModel
        ) {
            intent.putExtra(CPU_BRAND_ID, viewModel.cpuBrand.value)
            intent.putExtra(SOCKET_ID, viewModel.socketType.value)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_build_component,
                    parent,
                    false
                )
        )

    override fun getItemCount() = componentList.size

    override fun onBindViewHolder(holder: BuildingAdapter.ViewHolder, position: Int) {
        holder.bindView(context, componentList[position], viewModel, position)
    }

    fun updateList(componentCategoryName: MutableList<String>) {
        componentList = componentCategoryName
        notifyDataSetChanged()
    }

}