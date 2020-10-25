package com.arya199.gemstone.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
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
    }

    private fun changeCurrency(code: String) {
        Timber.w("Selected code $code")
        // Check the amount input, if it null, empty, ignore this entirely.
        val amount = viewDataBinding.currencyInputAmountText.text.toString()
        if (amount.isNullOrBlank()) return
        // Non number, display toast,
        else {
            val numeric = amount.matches("\\d+(\\.\\d+)?".toRegex())
            if (!numeric) {
                Toast.makeText(context, "Input only positive number", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, "Proceed $code", Toast.LENGTH_LONG).show()
                sharedViewModel.convert(amount.toDouble(), code)
            }
        }
    }
}