package com.github.friendlytrainer.storage

import com.github.friendlytrainer.storage.AppStorage

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppStorage(databaseDriverFactory.createDriver())
    private val dbQuery = database.appStorageQueries

    internal fun clearData() {
        dbQuery.transaction {
            dbQuery.clearHistory()
        }
    }

    //internal fun
}