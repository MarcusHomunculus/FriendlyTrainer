package com.github.friendlytrainer.android.mixins

import android.content.Context
import com.github.friendlytrainer.android.storage.Storage
import com.github.friendlytrainer.storage.DatabaseDriverFactory
import com.github.friendlytrainer.TrainerData
import com.github.friendlytrainer.android.storage.Persistent

interface SharedDataStoring {

    fun deriveSharedDatabaseHandle(context: Context) : Storage {
        return Persistent(TrainerData(DatabaseDriverFactory(context)))
    }
}