package com.arya199.gemstone.rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya199.gemstone.data.Rate
import kotlinx.coroutines.launch
import javax.inject.Inject

class RateViewModel @Inject constructor(): ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _rates = MutableLiveData<List<Rate>>().apply { value = emptyList() }
    val rates: LiveData<List<Rate>> = _rates

    fun loadRates() {
        _dataLoading.value = true
        viewModelScope.launch {

        }
    }
}