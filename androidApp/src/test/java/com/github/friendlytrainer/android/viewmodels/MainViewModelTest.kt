package com.github.friendlytrainer.android.viewmodels

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class MainViewModelTest {

    @Test
    @Tag("unittest")
    fun test_run() {
        assertTrue(true)
    }

    @Test
    @Tag("unittest")
    fun run_2() {
        assertTrue(false == false)
    }
}