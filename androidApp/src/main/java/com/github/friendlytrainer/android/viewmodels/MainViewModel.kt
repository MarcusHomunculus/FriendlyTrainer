package com.github.friendlytrainer.android.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.github.friendlytrainer.android.R

class MainViewModel : ViewModel() {

    enum class InfoView { AMEND, PROGRESS }

    private var _active: InfoView = InfoView.AMEND

    fun focus(which: InfoView) {
        Log.i("MainViewModel", "Focus update requested")
        _active = which;
    }

    fun amendVisibility(): Int {
        return if (hasFocus(InfoView.AMEND)) View.VISIBLE else View.GONE
    }

    fun amendIconResource(): Int {
        return if (hasFocus(InfoView.AMEND)) R.drawable.collapse_arrow else R.drawable.expand_arrow
    }

    fun progressVisibility(): Int {
        return if (hasFocus(InfoView.PROGRESS)) View.VISIBLE else View.GONE
    }

    fun progressIconResource(): Int {
        return if (hasFocus(InfoView.PROGRESS)) R.drawable.collapse_arrow else R.drawable.expand_arrow
    }

    private fun hasFocus(who: InfoView): Boolean {
        return _active == who
    }
}