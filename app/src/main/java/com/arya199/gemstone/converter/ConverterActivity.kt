package com.arya199.gemstone.converter

import android.os.Bundle
import com.arya199.gemstone.R
import dagger.android.support.DaggerAppCompatActivity

class ConverterActivity: DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.converter_act)
    }

    fun getSomething(): String {
        return "something"
    }
}