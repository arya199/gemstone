package com.arya199.gemstone.rate

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arya199.gemstone.R
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.resIdByName
import timber.log.Timber

@BindingAdapter("app:rates")
fun setRates(listView: RecyclerView, rates: List<Rate>) {
    rates.let {
        (listView.adapter as RateAdapter).submitList(rates)
    }
}

@BindingAdapter(value = ["app:flag", "app:context"])
fun setFlag(imageView: ImageView, svgSrc: String, context: Context) {
    val resourceName = "ic_${svgSrc.toLowerCase()}"
    val resourceInt = context.resIdByName(resourceName, "drawable")
    if (resourceInt != 0) {
        imageView.setImageResource(resourceInt)
    }
    else {
        imageView.setImageResource(R.drawable.ic_none)
    }
}