package com.arya199.gemstone

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerRepository
import com.arya199.gemstone.data.source.remote.CurrencyLayerRemoteDataSource
import com.arya199.gemstone.data.succeeded
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

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Context>() as TestGemstoneApplication
        component = DaggerTestApplicationComponent.factory().create(app)
        repository = component.currencyLayerRepository
    }

    @Test
    fun testLiveApi() {
        runBlocking {
            val result = repository.getRates()
            if (result.succeeded) {
                when (result) {
                    is Result.Success -> {
                        val liveResponse = result
                        liveResponse.toString()
                        print(liveResponse.toString())
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