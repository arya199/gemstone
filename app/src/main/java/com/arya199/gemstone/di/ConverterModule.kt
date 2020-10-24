package com.arya199.gemstone.di

import androidx.lifecycle.ViewModel
import com.arya199.gemstone.converter.ConverterActivity
import com.arya199.gemstone.rate.RateGridFragment
import com.arya199.gemstone.converter.ConverterInputFragment
import com.arya199.gemstone.converter.CurrencyViewModel
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import com.arya199.gemstone.data.source.DefaultCurrencyLayerRepository
import com.arya199.gemstone.data.source.local.CurrencyLayerLocalDataSource
import com.arya199.gemstone.data.source.remote.CurrencyLayerRemoteDataSource
import com.arya199.gemstone.data.source.remote.FakeRemoteDataSource
import com.arya199.gemstone.rate.RateViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
abstract class ConverterModule {

    @ContributesAndroidInjector
    internal abstract fun ConverterActivity(): ConverterActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun ConverterInputFragment(): ConverterInputFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun ConverterGridFragment(): RateGridFragment

    @Singleton
    @Binds
    abstract fun bindCurrencyLayerRepository(repository: DefaultCurrencyLayerRepository)
        : CurrencyLayerRepository

    @Binds
    @IntoMap
    @ViewModelKey(RateViewModel::class)
    abstract fun bindRateViewModel(viewModel: RateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    abstract fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    @RealRemoteDataSourceAnnotation
    internal abstract fun bindCurrencyLayerRemoteDataSource(dataSource:
        CurrencyLayerRemoteDataSource): CurrencyLayerDataSource

    @Binds
    @FakeRemoteDataSourceAnnotation
    internal abstract fun bindFakeRemoteDataSource(dataSource:
        FakeRemoteDataSource): CurrencyLayerDataSource

    @Binds
    @LocalDataSourceAnnotation
    internal abstract fun bindLocalDataSource(dataSource:
        CurrencyLayerLocalDataSource): CurrencyLayerDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RealRemoteDataSourceAnnotation

    /**
     * This shouldn't be here.
     */
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class FakeRemoteDataSourceAnnotation

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSourceAnnotation
}