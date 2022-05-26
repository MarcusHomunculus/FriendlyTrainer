package com.github.friendlytrainer.android.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.friendlytrainer.android.R

class MainViewModel : ViewModel() {

    enum class InfoView { AMEND, PROGRESS }
    class CardState(isExpanded: Boolean) {
        val arrowIcon = if (isExpanded) R.drawable.collapse_arrow else R.drawable.expand_arrow
        val visibility = if (isExpanded) View.VISIBLE else View.GONE
    }
    class ViewState(active: InfoView) {
        val amend = CardState(active == InfoView.AMEND)
        val progress = CardState(active == InfoView.PROGRESS)
    }

    private var _state: MutableLiveData<ViewState> = MutableLiveData(ViewState(InfoView.AMEND))
    val state: LiveData<ViewState> get() = _state

    fun focus(which: InfoView) {
        Log.i("MainViewModel", "Focus update requested")
        _state.value = ViewState(which)
    }
}