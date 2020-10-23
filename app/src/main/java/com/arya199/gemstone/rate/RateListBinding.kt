package com.arya199.gemstone.rate

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arya199.gemstone.data.Rate

@BindingAdapter("app:rates")
fun setRates(listView: RecyclerView, rates: List<Rate>) {
    rates.let {
        (listView.adapter as RateAdapter).submitList(rates)
    }
}