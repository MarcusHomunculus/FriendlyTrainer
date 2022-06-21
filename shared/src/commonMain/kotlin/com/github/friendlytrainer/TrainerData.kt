package com.github.friendlytrainer

import com.github.friendlytrainer.ExerciseRecord
import com.github.friendlytrainer.storage.Database
import com.github.friendlytrainer.storage.DatabaseDriverFactory
import kotlinx.datetime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class TrainerData(driverFactory: DatabaseDriverFactory, val scope: CoroutineScope) {

    data class SimpleDate(val month: Int, val day: Int)
    data class SingleExerciseRecord(val howMany: Int, val at: SimpleDate)

    private val _database = Database(driverFactory)

    fun registerCountFlow(counts: Flow<Int>) {
        scope.launch {
            collectCounts(counts.flowOn(Dispatchers.IO))
        }
    }

    fun historyFlow(): Flow<List<SingleExerciseRecord>> = flow {
        while (true) {
            emit(pullFullHistory())
        }
    }.flowOn(Dispatchers.Default)

    private suspend fun collectCounts(counts: Flow<Int>) {
        counts.collect { count ->
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val new = ExerciseRecord(today.toString(), "Sit-up", count.toUInt())
            _database.addExercise(new)
        }
    }

    private suspend fun pullFullHistory(): List<SingleExerciseRecord> {
        return _database.allExercises()
            .map { Pair(it.count.toInt(), LocalDate.parse(it.date)) }
            .map { SingleExerciseRecord(it.first, SimpleDate(it.second.monthNumber, it.second.dayOfMonth)) }
    }
}