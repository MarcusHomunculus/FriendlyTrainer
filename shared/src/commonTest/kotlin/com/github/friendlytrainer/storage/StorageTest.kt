package com.github.friendlytrainer.storage

import com.github.friendlytrainer.TrainerData
import kotlin.test.*
import kotlinx.datetime.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class StorageTest: RoboelectricTests() {

    private val _mockYear = 2010
    private val _mockMonth = 6
    private val _mockDay = 1
    private val _mockZone = TimeZone.UTC
    private val _clockMock = mock<Clock> {
        on { now() } doReturn "${_mockYear}-0${_mockMonth}-0${_mockDay}T22:19:44.475${_mockZone}".toInstant()
    }

    @Test
    fun `store exercise`() {
        val testCount = 5
        val uut = TrainerData(createTestDriverFactory(), _clockMock, _mockZone)
        uut.addExercise(testCount)
        val content = uut.history().first()
        assertEquals(content.howMany, testCount)
        assertEquals(content.at, TrainerData.SimpleDate(_mockMonth, _mockDay))
    }
}