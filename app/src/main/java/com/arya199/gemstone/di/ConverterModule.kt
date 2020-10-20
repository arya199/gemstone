package com.arya199.gemstone.di

import com.arya199.gemstone.converter.ConverterActivity
import com.arya199.gemstone.converter.ConverterGridFragment
import com.arya199.gemstone.converter.ConverterInputFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ConverterModule {

    @ContributesAndroidInjector
    internal abstract fun ConverterActivity(): ConverterActivity

    @ContributesAndroidInjector
    internal abstract fun ConverterInputFragment(): ConverterInputFragment

    @ContributesAndroidInjector
    internal abstract fun ConverterGridFragment(): ConverterGridFragment
}