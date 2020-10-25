package com.arya199.gemstone.converter

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya199.gemstone.Event
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val currencyLayerRepository: CurrencyLayerRepository
): ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>().apply { value = emptyList() }
    val currencies: LiveData<List<Currency>> = _currencies

    // String on this event is the currency's three digits code
    private val _selectCurrencyEvent = MutableLiveData<Event<String>>()
    val selectCurrencyEvent: LiveData<Event<String>> = _selectCurrencyEvent

    val selectedCurrency = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _selectCurrencyEvent.value = Event(this@apply.get()!!)
            }
        })
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            val currencyList = currencyLayerRepository.getExchangeList()
            if (currencyList is Result.Success) {
                val currencies = currencyList.data
                val currenciesToShow = ArrayList<Currency>()
                for (currency in currencies) {
                    currenciesToShow.add(currency)
                }
                _currencies.value = ArrayList(currenciesToShow)
            }
        }
    }
}