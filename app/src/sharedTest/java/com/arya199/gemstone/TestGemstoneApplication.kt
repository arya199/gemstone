package com.arya199.gemstone

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestGemstoneApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTestApplicationComponent.factory().create(applicationContext)
    }
}