package com.github.friendlytrainer.android.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.friendlytrainer.android.storage.Storage
import com.github.friendlytrainer.android.viewmodels.MainViewModel

class MainViewModelFactory(private val _data: Storage) : ViewModelProvider.Factory {

    companion object {
        private var instance: MainViewModel? = null
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (instance == null)
            instance = MainViewModel(_data)
        return instance as T
    }
}