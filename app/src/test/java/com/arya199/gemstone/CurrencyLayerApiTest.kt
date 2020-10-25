package com.arya199.gemstone

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import com.arya199.gemstone.data.source.local.GemstoneDatabase
import com.arya199.gemstone.data.source.remote.FakeRemoteDataSource
import com.arya199.gemstone.data.succeeded
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class CurrencyLayerApiTest {

    private lateinit var component: TestApplicationComponent
    lateinit var repository: CurrencyLayerRepository
    lateinit var fakeDataSource: CurrencyLayerDataSource
    lateinit var db: GemstoneDatabase

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Context>() as TestGemstoneApplication
        component = DaggerTestApplicationComponent.factory().create(app)
        repository = component.currencyLayerRepository
        fakeDataSource = FakeRemoteDataSource()
        db = component.db
    }

    @Test
    fun testLiveApi() {
        runBlocking {
            val result = repository.getRates(true)
            if (result.succeeded) {
                when (result) {
                    is Result.Success -> {
                        print(result.toString())
                        assert(true)
                    }
                    is Result.Error -> {
                        Result.Error(result.exception)
                        assert(false)
                    }
                }
            }
        }
    }

    @Test
    fun testListApi() {
        runBlocking {
            val result = repository.getExchangeList()
            if (result.succeeded) {
                when (result) {
                    is Result.Success -> {
                        print(result.toString())
                        assert(true)
                    }
                    is Result.Error -> {
                        Result.Error(result.exception)
                        assert(false)
                    }
                }
            }
        }
    }

    @Test
    fun testListApi_then_CheckLocalDatabase() {
        runBlocking {
            val result = repository.getExchangeList()
            if (result.succeeded) {
                when (result) {
                    is Result.Success -> {
                        assert(true)
                    }
                    is Result.Error -> {
                        print(result.exception)
                        assert(false)
                    }
                }
            }
            // This should not hit remote.
            repository.getExchangeList()
            // Check local database
            val currencyList = db.currencyDao().getCurrencies()
            assertEquals(168, currencyList.size)
        }
    }

    @Test
    fun testLiveApi_then_CheckLocaDatabase() {
        runBlocking {
            val result = repository.getRates(false)
            if (result.succeeded) {
                when (result) {
                    is Result.Success -> {
                        assert(true)
                    }
                    is Result.Error -> {
                        print(result.exception)
                        assert(false)
                    }
                }
            }
            // This should not hit remote.
            repository.getRates(false)
            // Check local database
            val rateList = db.rateDao().getRates()
            assertEquals(168, rateList.size)
        }
    }

    @Test
    fun testFakeApi() {
        runBlocking {
            val result = fakeDataSource.getLiveRate()
            if (result.succeeded) {
                when (result) {
                    is Result.Success -> {
                        print(result.toString())
                        assert(true)
                    }
                    is Result.Error -> {
                        Result.Error(result.exception)
                        assert(false)
                    }
                }
            }
        }
    }
}