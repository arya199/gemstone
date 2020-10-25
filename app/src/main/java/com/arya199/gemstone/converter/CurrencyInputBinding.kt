package com.arya199.gemstone.converter

import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import com.arya199.gemstone.data.Currency

@BindingAdapter("app:currencies")
fun setCurrencies(spinner: Spinner, currencies: List<Currency>) {
    currencies.let {
        (spinner.adapter as CurrencySpinnerAdapter).setItems(currencies)
        spinner.setSelection((spinner.adapter as CurrencySpinnerAdapter).getDefaultPosition())
    }
}

@BindingAdapter(value = ["app:onCurrencyChange", "app:onCurrencyChangeChanged"], requireAll = false)
fun onCurrencyChange(spinner: Spinner, result: ObservableField<String>, changeListener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (parent != null) {
                result.set(parent.getItemAtPosition(position).toString())
            }
            changeListener.onChange()
        }
    }
}

@InverseBindingAdapter(attribute = "app:onCurrencyChange", event = "app:onCurrencyChangeChanged")
fun getCurrencyChange(spinner: Spinner): String {
    return spinner.selectedItem.toString()
}

@BindingAdapter(value = ["app:onAmountChange", "app:onAmountChangeChanged"], requireAll = false)
fun onAmountChange(editText: EditText, result: ObservableField<String>, changeListener: InverseBindingListener) {
    editText.setOnKeyListener { view, keyCode, keyEvent ->
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
            result.set(editText.text.toString())
            true
        }
        else {
            false
        }
    }
}

@InverseBindingAdapter(attribute = "app:onAmountChange", event = "app:onAmountChangeChanged")
fun getAmount(editText: EditText): String {
    return editText.text.toString()
}