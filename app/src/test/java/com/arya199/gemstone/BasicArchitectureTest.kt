package com.arya199.gemstone

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import com.arya199.gemstone.data.source.local.CurrencyLayerLocalDataSource
import com.arya199.gemstone.data.source.local.GemstoneDatabase
import com.arya199.gemstone.di.ConverterModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class BasicArchitectureTest {

    private lateinit var component: TestApplicationComponent
    lateinit var retrofit: Retrofit
        private set
    lateinit var database: GemstoneDatabase

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Context>() as TestGemstoneApplication
        component = DaggerTestApplicationComponent.factory().create(app)
        retrofit = component.retrofit
        database = component.db
    }

    @Test
    fun testRetrofit() {
        print(retrofit.baseUrl())
    }

    @Test
    fun testDatabase() {
        runBlocking {
            // TODO: It doesn't work, somehow? currencyList is always zero
            val currencyList = database.currencyDao().getCurrencies()
            print(currencyList.size)
        }
    }
}