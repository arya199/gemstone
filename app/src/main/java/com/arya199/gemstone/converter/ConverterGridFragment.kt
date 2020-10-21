package com.arya199.gemstone.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arya199.gemstone.databinding.ConverterGridFragBinding
import dagger.android.support.DaggerFragment

class ConverterGridFragment: DaggerFragment() {

    private lateinit var viewDataBinding : ConverterGridFragBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ConverterGridFragBinding.inflate(inflater, container, false)
            .apply {

            }
        return viewDataBinding.root
    }
}