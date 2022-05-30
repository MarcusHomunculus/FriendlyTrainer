package com.github.friendlytrainer

import com.github.friendlytrainer.ExerciseRecord
import com.github.friendlytrainer.storage.Database
import com.github.friendlytrainer.storage.DatabaseDriverFactory
import kotlinx.datetime.*

class TrainerData(driverFactory: DatabaseDriverFactory) {

    data class SimpleDate(val month: Int, val day: Int)
    data class SingleExerciseRecord(val howMany: Int, val at: SimpleDate)

    private val _database = Database(driverFactory)

    fun getHistory(): List<SingleExerciseRecord> {
        // TODO: cache here?!
        return _database.allExercises()
            .map { Pair(it.count.toInt(), LocalDate.parse(it.date)) }
            .map { SingleExerciseRecord(it.first, SimpleDate(it.second.monthNumber, it.second.dayOfMonth)) }
    }

    fun add(count: Int) {
        // val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val new = ExerciseRecord(today.toString(), "Sit-up", count.toUInt())
        _database.addExercise(new)
    }
}