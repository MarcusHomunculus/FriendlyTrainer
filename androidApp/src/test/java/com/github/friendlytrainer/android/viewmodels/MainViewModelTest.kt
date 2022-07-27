package com.github.friendlytrainer.android.viewmodels

import android.view.View
import com.androidplot.xy.SimpleXYSeries
import com.github.friendlytrainer.android.rules.InstantExecutorExtension
import com.github.friendlytrainer.android.storage.Storage
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify

@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    @Test @Tag("unittest")
    fun `ensure active fragment is mutually exclusive`() {
        val mock = mock<Storage>()
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test @Tag("unittest")
    fun `write count to database`() = runTest {
        val mock = mock<Storage>()
        val uut = MainViewModel(mock, this)
        val count = 5
        uut.newCount.set("$count")
        uut.commitNewCount()
        delay(100)  // The callbacks need some time -> FIXME!!
        val captor = argumentCaptor<Int>()
        verify(mock).addExercise(captor.capture())
        assertEquals(count, captor.firstValue)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test @Tag("unittest")
    fun `read all from database`() = runTest {
        val xVals: List<Number> = listOf(1,2)
        val yVals: List<Number> = listOf(3,4)
        val dates = listOf(
            MainViewModel.DateStruct(1,1),
            MainViewModel.DateStruct(2, 1)
        )
        val mock = mock<Storage> {
                onBlocking { history() }.doReturn(Pair(SimpleXYSeries(xVals, yVals, "test series"), dates))
        }
        val uut = MainViewModel(mock, this)
        val actualFuture = uut.requestHistory()
        val actual = actualFuture.await()
        for (i in xVals.indices)
            assertEquals(actual.first.getX(i), xVals[i])
        for (i in yVals.indices)
            assertEquals(actual.first.getY(i), yVals[i])
        for (i in dates.indices)
            assertEquals(actual.second[i], dates[i])
    }
}