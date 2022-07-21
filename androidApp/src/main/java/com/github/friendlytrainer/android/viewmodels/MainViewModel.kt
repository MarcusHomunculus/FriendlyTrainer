package com.github.friendlytrainer.android.viewmodels

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.androidplot.xy.XYSeries
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.android.storage.Storage
import kotlinx.coroutines.*

class MainViewModel(private val _data: Storage, externalScope: CoroutineScope? = null): ViewModel() {

    private val _scope = externalScope ?: viewModelScope

    enum class InfoView { AMEND, PROGRESS }
    class CardState(isExpanded: Boolean) {
        val arrowIcon = if (isExpanded) R.drawable.collapse_arrow else R.drawable.expand_arrow
        val visibility = if (isExpanded) View.VISIBLE else View.GONE
    }
    class ViewState(active: InfoView) {
        val amend = CardState(active == InfoView.AMEND)
        val progress = CardState(active == InfoView.PROGRESS)
    }
    data class DateStruct(val day: Int, val month: Int)

    private var _state: MutableLiveData<ViewState> = MutableLiveData(ViewState(InfoView.AMEND))
    private var _amendButtonState: MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)
    private var _countValue: MutableLiveData<Int> = MutableLiveData()

    val state: LiveData<ViewState> get() = _state
    val amendButtonState: LiveData<Int> get() = _amendButtonState
    val reinforcementText: LiveData<String> get() = Transformations.map(_countValue) { nextReinforcementText(it) }
    val newCount: ObservableField<String> = ObservableField()

    init {
        newCount.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                _amendButtonState.value = deriveAmendButtonState()
            }
        })
        _countValue.observeForever { pushExercise(it) }
    }

    fun focus(which: InfoView) {
        _state.value = ViewState(which)
        _amendButtonState.value = deriveAmendButtonState()
    }

    fun commitNewCount() {
        _countValue.value = newCount.get()!!.toInt()
    }

    fun requestHistory(): Deferred<Pair<XYSeries, List<DateStruct>>> = _scope.async {
        _data.history()
    }

    private fun nextReinforcementText(new: Int): String = "$new is awesome!"

    private fun deriveAmendButtonState(): Int {
        return if (_state.value!!.amend.visibility == View.GONE)
            View.GONE
        else if (newCount.get().isNullOrEmpty())
            View.GONE
        else
            View.VISIBLE
    }

    private fun pushExercise(count: Int) = _scope.launch { _data.addExercise(count) }
}
