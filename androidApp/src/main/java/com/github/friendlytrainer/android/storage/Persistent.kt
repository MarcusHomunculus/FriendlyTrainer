package com.github.friendlytrainer.android.storage

import com.androidplot.xy.XYSeries
import com.github.friendlytrainer.TrainerData
import com.github.friendlytrainer.android.viewmodels.MainViewModel

class Persistent(private val _source: TrainerData) : Storage() {

    override suspend fun addExercise(count: Int) = _source.addExercise(count)

    override suspend fun history(): Pair<XYSeries, List<MainViewModel.DateStruct>> {
        return translate(_source.history())
    }
}