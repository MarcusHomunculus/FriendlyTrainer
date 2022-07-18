package com.github.friendlytrainer.android.viewmodels

import android.app.Application
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.androidplot.xy.XYSeries
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.android.storage.Storage
import kotlinx.coroutines.async
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

class MainViewModel(private val _data: Storage) : ViewModel() {

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

    fun requestHistory(): Deferred<Pair<XYSeries, List<DateStruct>>> = viewModelScope.async {
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

    private fun pushExercise(count: Int) = viewModelScope.launch { _data.addExercise(count) }

    /*
    private fun List<TrainerData.SingleExerciseRecord>.split(): Pair<XYSeries, List<DateStruct>> {
        val pairs = this.map { Pair(it.howMany, DateStruct(it.at.day, it.at.month)) }
        val series = SimpleXYSeries(pairs.map { it.first }, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sit-ups")
        return Pair(series, pairs.map { it.second })
    }
    */
}
