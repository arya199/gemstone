package com.arya199.gemstone.rate

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya199.gemstone.Event
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

class RateViewModel @Inject constructor(
    private val currencyLayerRepository: CurrencyLayerRepository
): ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _rates = MutableLiveData<List<Rate>>().apply { value = emptyList() }
    val rates: LiveData<List<Rate>> = _rates

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

    private val _inputAmountEvent = MutableLiveData<Event<String>>()
    val inputAmountEvent: LiveData<Event<String>> = _inputAmountEvent

    val inputAmount = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputAmountEvent.value = Event(this@apply.get()!!)
            }
        })
    }

    fun loadRates(shouldUpdateLive: Boolean, update: (Long) -> Unit) {
        _dataLoading.value = true
        viewModelScope.launch {
            val rateResult = currencyLayerRepository.getRates(shouldUpdateLive)
            if (rateResult is Result.Success) {
                val rates = rateResult.data
                val ratesToShow = ArrayList<Rate>()
                for (rate in rates) {
                    rate.rate = rate.rate?.let { roundDouble(it) }
                    ratesToShow.add(rate)
                }
                _rates.value = ArrayList(ratesToShow)
                update(System.currentTimeMillis())
            }
            else {
                // TODO: If error, figure out what to tell user about it.
            }
        }
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

    fun convert(amount: Double, from: String) {
        viewModelScope.launch {
            val showRates = currencyLayerRepository.getRates(false)
            if (showRates is Result.Success) {
                val rates = showRates.data
                var usdBase = 1.0
                if (!from.equals("USD", true)) {
                    val indexForRate = rates.indexOfFirst {
                        it.to.equals(from, true)
                    }
                    usdBase = 1/(rates[indexForRate].rate!!)*amount
                }
                val newRateToShow = ArrayList<Rate>()
                for (rate in rates) {
                    // The most straightforward way is when the from is USD so do that first
                    if (from.equals("USD", true)) {
                        newRateToShow.add(Rate(from, rate.to,
                            rate.rate?.times(amount)?.let { roundDouble(it) }, rate.fullText))
                    }
                    else {
                        if (rate.to.equals(from, true)) {
                            newRateToShow.add(Rate(from, "USD", roundDouble(usdBase), rate.fullText))
                        }
                        else {
                            newRateToShow.add(Rate(from, rate.to,
                                rate.rate?.times(usdBase)?.let { roundDouble(it) },
                                rate.fullText))
                        }
                    }
                }
                _rates.value = ArrayList(newRateToShow)
            }
        }
    }

    private fun roundDouble(rate: Double): Double {
        val factor = 10.0.pow(4.toDouble())
        return (rate * factor).roundToInt() / factor
    }
}