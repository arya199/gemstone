package com.arya199.gemstone.rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RateViewModel @Inject constructor(
    private val currencyLayerRepository: CurrencyLayerRepository
): ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _rates = MutableLiveData<List<Rate>>().apply { value = emptyList() }
    val rates: LiveData<List<Rate>> = _rates

    fun loadRates() {
        _dataLoading.value = true
        viewModelScope.launch {
            val rateResult = currencyLayerRepository.getRates()
            if (rateResult is Result.Success) {
                val rates = rateResult.data
                val ratesToShow = ArrayList<Rate>()
                for (rate in rates) {
                    ratesToShow.add(rate)
                }
                _rates.value = ArrayList(ratesToShow)
            }
            else {
                // TODO: If error, figure out what to tell user about it.
            }
        }
    }
}