package com.arya199.gemstone.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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