package com.github.friendlytrainer.android.viewmodels

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.friendlytrainer.android.rules.InstantExecutorExtension
import com.github.friendlytrainer.android.storage.Storage
import org.junit.Rule
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import com.jraska.livedata.test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule

@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    @Test @Tag("unittest")
    fun `test that active fragment is mutually exclusive`() {
        val mock = mock<Storage> { }
        val uut = MainViewModel(mock)
        // Info view is default
        uut.state.test()
            .assertValue {
                it.amend.visibility == View.VISIBLE && it.progress.visibility != View.VISIBLE
            }
        uut.focus(MainViewModel.InfoView.PROGRESS)
        uut.state.test()
            .assertValue {
                it.progress.visibility == View.VISIBLE && it.amend.visibility != View.VISIBLE
            }
    }
}