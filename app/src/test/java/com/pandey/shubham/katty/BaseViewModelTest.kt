package com.pandey.shubham.katty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pandey.shubham.katty.util.CoroutineTestRule
import com.pandey.shubham.katty.util.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

/**
 * Created by shubhampandey
 */
open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    fun runTest(block: suspend TestScope.() -> Unit) = coroutineRule.runTest(block)
}