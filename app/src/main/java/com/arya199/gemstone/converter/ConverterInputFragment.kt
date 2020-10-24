package com.arya199.gemstone.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.arya199.gemstone.databinding.ConverterInputFragBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ConverterInputFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val sharedViewModel by activityViewModels<CurrencyViewModel> {
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
        setupSpinnerAdapter()
        sharedViewModel.loadCurrencies()
    }

    private fun setupSpinnerAdapter() {
        val viewModel = viewDataBinding.currencyViewModel
    }
}