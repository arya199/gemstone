package com.arya199.gemstone.converter

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.arya199.gemstone.Event
import com.arya199.gemstone.EventObserver
import com.arya199.gemstone.databinding.ConverterInputFragBinding
import com.arya199.gemstone.rate.RateViewModel
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class ConverterInputFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val sharedViewModel by activityViewModels<RateViewModel> {
        viewModelFactory
    }

    private lateinit var viewDataBinding: ConverterInputFragBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ConverterInputFragBinding.inflate(inflater, container, false)
            .apply {
                currencyViewModel = sharedViewModel
            }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSpinnerAdapter()
        sharedViewModel.loadCurrencies()
        listenToChange()
    }

    private fun setupSpinnerAdapter() {
        val viewModel = viewDataBinding.currencyViewModel
        if (viewModel != null) {
            val adapter = CurrencySpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item)
            viewDataBinding.currencyInputSpinner.adapter = adapter
        }
    }

    private fun listenToChange() {
        sharedViewModel.selectCurrencyEvent.observe(viewLifecycleOwner, EventObserver {
            changeCurrency(it)
        })
        sharedViewModel.inputAmountEvent.observe(viewLifecycleOwner, EventObserver {
            changeAmount(it)
        })
    }

    private fun changeAmount(amount: String) {
        // Check the selected currency.
        val currencyCode = viewDataBinding.currencyInputSpinner.selectedItem.toString()
        // If that amount is null or blank, ignore entirely.
        if (amount.isNullOrBlank()) {
            return
        }
        else {
            val numeric = amount.matches("\\d+(\\.\\d+)?".toRegex())
            if (!numeric) {
                Toast.makeText(context, "Input only positive number", Toast.LENGTH_LONG).show()
            }
            else {
                sharedViewModel.convert(amount.toDouble(), currencyCode)
            }
        }
    }

    private fun changeCurrency(code: String) {
        // Check the amount input, if it null, empty, ignore this entirely.
        val amount = viewDataBinding.currencyInputAmountText.text.toString()
        if (amount.isNullOrBlank()) {
            // Actually, if it is blank, set the amount to 1.
            viewDataBinding.currencyInputAmountText.setText("1")
            return
        }
        // Non number, display toast,
        else {
            val numeric = amount.matches("\\d+(\\.\\d+)?".toRegex())
            if (!numeric) {
                Toast.makeText(context, "Input only positive number", Toast.LENGTH_LONG).show()
            }
            else {
                sharedViewModel.convert(amount.toDouble(), code)
            }
        }
    }
}