package com.arya199.gemstone.converter

import android.content.Context
import android.widget.ArrayAdapter
import com.arya199.gemstone.data.Currency

class CurrencySpinnerAdapter(context: Context, resource: Int): ArrayAdapter<String>(context, resource) {

    private var _items = ArrayList<Currency>()

    fun setItems(currencies: List<Currency>) {
        _items.clear()
        _items.addAll(currencies)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return _items.size
    }

    override fun getItem(position: Int): String? {
        return _items[position].code
    }
}