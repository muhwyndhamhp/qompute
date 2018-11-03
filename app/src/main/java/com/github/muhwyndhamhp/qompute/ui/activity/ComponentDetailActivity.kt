package com.github.muhwyndhamhp.qompute.ui.activity

import android.annotation.SuppressLint
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.github.muhwyndhamhp.qompute.R
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.utils.CATEGORY_CODE
import com.github.muhwyndhamhp.qompute.utils.COMPONENT_CODE
import com.github.muhwyndhamhp.qompute.utils.InjectorUtils
import com.github.muhwyndhamhp.qompute.viewmodel.ComponentDetailViewModel
import kotlinx.android.synthetic.main.activity_component_detail.*
import java.text.NumberFormat
import java.util.*

class ComponentDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ComponentDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component_detail)

        val factory = InjectorUtils.provideComponentDetailViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(ComponentDetailViewModel::class.java)

        if(actionBar!= null) actionBar!!.setDisplayHomeAsUpEnabled(true)

        initActivity()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initActivity() {
        val component = intent.getSerializableExtra(COMPONENT_CODE) as Component
        tv_name.text = component.name
        tv_brand_desc.text = component.brandDescription
        tv_category_desc.text = component.categoryDescription
        tv_subcategory_desc.text = component.subcategoryDescription
        tv_price_desc.text = NumberFormat.getCurrencyInstance(Locale("in","ID")).format(component.price.toLong())
        if(component.linkToped != "" || component.linkBukalapak != "" || component.linkShopee != "") layout_buy_link.visibility = View.VISIBLE
        when{
            component.linkToped != "" -> iv_tokopedia.visibility = View.VISIBLE
            component.linkBukalapak != "" -> iv_bukalapak.visibility = View.VISIBLE
            component.linkShopee != "" -> iv_shopee.visibility = View.VISIBLE
        }
        val url = component.name.replace(" ", "%20")

        web_view.clearCache(true);
        web_view.clearHistory();
        web_view.settings.javaScriptEnabled = true;
        web_view.isNestedScrollingEnabled = true
        web_view.settings.javaScriptCanOpenWindowsAutomatically = true;
        web_view.loadUrl("https://www.google.com/search?tbm=isch&q=$url&ie=utf-8&oe=utf-8")

        setImageView()
    }

    private fun setImageView() {

        val categories = resources.getStringArray(R.array.component_endpoint)
        when (intent.getStringExtra(CATEGORY_CODE)) {
            categories[0] -> Glide.with(this).load(R.drawable.ic_case).into(iv_category)
            categories[1] -> Glide.with(this).load(R.drawable.ic_fan).into(iv_category)
            categories[2] -> Glide.with(this).load(R.drawable.ic_hard_disk).into(iv_category)
            categories[3] -> Glide.with(this).load(R.drawable.ic_keyboard).into(iv_category)
            categories[4] -> Glide.with(this).load(R.drawable.ic_computer).into(iv_category)
            categories[5] -> Glide.with(this).load(R.drawable.ic_ram).into(iv_category)
            categories[6] -> Glide.with(this).load(R.drawable.ic_motherboard).into(iv_category)
            categories[7] -> Glide.with(this).load(R.drawable.ic_optical).into(iv_category)
            categories[8] -> Glide.with(this).load(R.drawable.ic_printer).into(iv_category)
            categories[9] -> Glide.with(this).load(R.drawable.ic_cpu).into(iv_category)
            categories[10] -> Glide.with(this).load(R.drawable.ic_supply).into(iv_category)
            categories[11] -> Glide.with(this).load(R.drawable.ic_card_reader).into(iv_category)
            categories[12] -> Glide.with(this).load(R.drawable.ic_speaker).into(iv_category)
            categories[13] -> Glide.with(this).load(R.drawable.ic_graphic_card).into(iv_category)
        }
    }
}
