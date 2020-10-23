package com.arya199.gemstone

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class BasicArchitectureTest {

    private lateinit var component: TestApplicationComponent
    lateinit var retrofit: Retrofit
        private set

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Context>() as TestGemstoneApplication
        component = DaggerTestApplicationComponent.factory().create(app)
        retrofit = component.retrofit
    }

    @Test
    fun testRetrofit() {
        print(retrofit.baseUrl())
    }
}