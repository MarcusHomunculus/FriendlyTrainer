package com.github.friendlytrainer.android.storage

import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYSeries
import com.github.friendlytrainer.android.viewmodels.MainViewModel
import com.github.friendlytrainer.TrainerData

abstract class Storage {

    abstract suspend fun addExercise(count: Int)

    abstract suspend fun history(): Pair<XYSeries, List<MainViewModel.DateStruct>>

    protected fun translate(original: List<TrainerData.SingleExerciseRecord>): Pair<XYSeries, List<MainViewModel.DateStruct>> {
        val pairs = original.map { Pair(it.howMany, MainViewModel.DateStruct(it.at.day, it.at.month)) }
        val series = SimpleXYSeries(pairs.map { it.first }, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sit-ups")
        return Pair(series, pairs.map { it.second })
    }
}