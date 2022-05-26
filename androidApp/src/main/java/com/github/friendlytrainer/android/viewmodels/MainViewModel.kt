package com.github.friendlytrainer.android.viewmodels

import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
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
    private var _amendButtonState: MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)
    val state: LiveData<ViewState> get() = _state
    val amendButtonState: LiveData<Int> get() = _amendButtonState
    val newCount: ObservableField<String> = ObservableField()

    init {
        newCount.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                _amendButtonState.value = deriveAmendButtonState()
            }
        })
    }

    fun focus(which: InfoView) {
        _state.value = ViewState(which)
        _amendButtonState.value = deriveAmendButtonState()
    }

    fun submitNewCount() {
        Log.i(MainViewModel::class.java.name, "Request to store ${newCount.get()} received")
    }

    private fun deriveAmendButtonState(): Int {
        return if (_state.value!!.amend.visibility == View.GONE)
            View.GONE
        else if (newCount.get().isNullOrEmpty())
            View.GONE
        else
            View.VISIBLE
    }
}