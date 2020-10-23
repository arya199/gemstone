package com.arya199.gemstone.di

import android.content.Context
import com.arya199.gemstone.GemstoneApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ConverterModule::class,
    AndroidInjectionModule::class,
    RetrofitModule::class
])
interface ApplicationComponent: AndroidInjector<GemstoneApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}