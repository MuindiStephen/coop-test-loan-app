package com.muindi.stephen.coopbanktest.testutils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Replacing Dispatchers.Main with a fake controllable, Test Dispatcher :)
 * TestWatcher - acts as a Base Class for rule - keeps track / logs of failing and passing tests
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    // Creating an instance of TestDispatcher & initializing it
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    //called before this test
    // to set state of given dispatcher as an underlying of Dispatchers.Main
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    //called after the test
    //reset state of Dispatchers.Main to the original main dispatcher
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}