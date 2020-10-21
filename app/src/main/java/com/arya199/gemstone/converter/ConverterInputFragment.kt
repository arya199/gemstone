package com.arya199.gemstone.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arya199.gemstone.databinding.ConverterInputFragBinding
import dagger.android.support.DaggerFragment

class ConverterInputFragment: DaggerFragment() {

    private lateinit var viewDataBinding: ConverterInputFragBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ConverterInputFragBinding.inflate(inflater, container, false)
            .apply {

            }
        return viewDataBinding.root
    }
}