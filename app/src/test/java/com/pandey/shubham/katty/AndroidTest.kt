package com.pandey.shubham.katty

import android.app.Application
import android.content.Context
import android.os.Build
import io.mockk.MockKAnnotations
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by shubhampandey
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidTest.ApplicationStub::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1])
abstract class AndroidTest {

    @Rule
    @JvmField
    @Suppress("LeakingThis")
    val injectMocks = InjectMocksRule.create(this@AndroidTest)

    fun context(): Context = RuntimeEnvironment.systemContext

    internal class ApplicationStub : Application()

    object InjectMocksRule {
        fun create(testClass: Any) = TestRule { statement, _ ->
            MockKAnnotations.init(testClass, relaxUnitFun = true)
            statement
        }
    }
}