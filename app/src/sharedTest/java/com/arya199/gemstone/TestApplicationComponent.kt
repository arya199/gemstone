package com.arya199.gemstone

import android.content.Context
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import com.arya199.gemstone.data.source.remote.CurrencyLayerRemoteDataSource
import com.arya199.gemstone.di.ConverterModule
import com.arya199.gemstone.di.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ConverterModule::class,
    RetrofitModule::class,
    AndroidInjectionModule::class
])
interface TestApplicationComponent: AndroidInjector<TestGemstoneApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
    }

    var retrofit: Retrofit

    var currencyLayerRepository: CurrencyLayerRepository
}