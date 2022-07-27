package com.github.friendlytrainer.storage

import com.squareup.sqldelight.db.SqlDriver

internal expect fun createTestDriverFactory(): DatabaseDriverFactory

expect abstract class RoboelectricTests()
