package com.github.friendlytrainer.storage

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

internal actual fun createTestDriverFactory(): DatabaseDriverFactory {
    val context = ApplicationProvider.getApplicationContext<Application>().applicationContext
    return DatabaseDriverFactory(context)
}

@RunWith(RobolectricTestRunner::class)
actual abstract class RoboelectricTests