package com.github.friendlytrainer

import com.github.friendlytrainer.ExerciseRecord
import com.github.friendlytrainer.storage.Database
import com.github.friendlytrainer.storage.DatabaseDriverFactory
import kotlinx.datetime.*

class TrainerData(driverFactory: DatabaseDriverFactory) {
    private val _database = Database(driverFactory)

    fun getFullHistory(): List<ExerciseRecord> {
        // TODO: cache here?!
        return _database.allExercises()
    }

    fun add(count: Int) {
        // val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val new = ExerciseRecord(today.toString(), "Sit-up", count.toUInt())
        _database.addExercise(new)
    }
}