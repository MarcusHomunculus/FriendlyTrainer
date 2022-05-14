package com.github.friendlytrainer.storage

import com.github.friendlytrainer.storage.AppStorage
import com.github.friendlytrainer.ExerciseRecord

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppStorage(databaseDriverFactory.createDriver())
    private val dbQuery = database.appStorageQueries

    internal fun clearData() {
        dbQuery.transaction {
            dbQuery.clearHistory()
        }
    }

    internal fun allExercises(): List<ExerciseRecord> {
        return dbQuery.fullHistory(::exerciseFactory).executeAsList()
    }

    internal fun lastExercise(): ExerciseRecord {
        return dbQuery.lastEntryHistory(::exerciseFactory).executeAsList().first()
    }

    internal fun addExercise(new: ExerciseRecord) {
        dbQuery.transaction {
            dbQuery.addExercise(new.date, new.exercise, new.count.toLong())
        }
    }

    private fun exerciseFactory(date: String, exercise: String, count: Long): ExerciseRecord {
        return ExerciseRecord(date, exercise, count.toUInt())
    }
}