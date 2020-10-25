package com.arya199.gemstone.converter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.databinding.CurrencyListItemBinding

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

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = _items[position]
        val retView: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder.from(parent)
            retView = viewHolder.getView()
            retView.tag = viewHolder
        }
        else {
            viewHolder = convertView.tag as ViewHolder
            retView = convertView
        }
        viewHolder.bind(item)
        return retView
    }

    fun getDefaultPosition(): Int {
        // TODO: Right now it's USD, but we can maybe set it up using SharedPreferences or something
        return _items.indexOfFirst {
            it.code.equals("USD", true)
        }
    }

    class ViewHolder private constructor(private val binding: CurrencyListItemBinding) {

        fun getView(): View {
            return binding.root
        }

        fun bind(item: Currency) {
            binding.currency = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = CurrencyListItemBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false)
                return ViewHolder(binding)
            }
        }
    }
}