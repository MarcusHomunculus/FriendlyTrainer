package com.github.friendlytrainer.android.viewmodels

import android.app.Application
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYSeries
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.storage.DatabaseDriverFactory
import com.github.friendlytrainer.TrainerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    enum class InfoView { AMEND, PROGRESS }
    class CardState(isExpanded: Boolean) {
        val arrowIcon = if (isExpanded) R.drawable.collapse_arrow else R.drawable.expand_arrow
        val visibility = if (isExpanded) View.VISIBLE else View.GONE
    }
    class ViewState(active: InfoView) {
        val amend = CardState(active == InfoView.AMEND)
        val progress = CardState(active == InfoView.PROGRESS)
    }

    private val _data: TrainerData = TrainerData(DatabaseDriverFactory(getApplication()))
    private var _state: MutableLiveData<ViewState> = MutableLiveData(ViewState(InfoView.AMEND))
    private var _amendButtonState: MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)
    private var _reinforcementText: MutableLiveData<String> = MutableLiveData()
    private var _countValue: MutableLiveData<Int> = MutableLiveData()

    val state: LiveData<ViewState> get() = _state
    val amendButtonState: LiveData<Int> get() = _amendButtonState
    val reinforcementText: LiveData<String> get() = _reinforcementText
    val newCount: ObservableField<String> = ObservableField()
    private val countValue: LiveData<Int> get() = _countValue

    init {
        newCount.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                _amendButtonState.value = deriveAmendButtonState()
            }
        })
        countValue.observe(getApplication()) { new ->
            // TODO: can this be realized without the additional live data object?!
            _reinforcementText.value = nextReinforcementText(new)
        }
        CoroutineScope(Dispatchers.IO).launch {
            _data.add(countValue.asFlow().flowOn(Dispatchers.Default))
        }
    }

    fun focus(which: InfoView) {
        _state.value = ViewState(which)
        _amendButtonState.value = deriveAmendButtonState()
    }

    fun commitNewCount() {
        _countValue.value = newCount.get()!!.toInt()
        // TODO: countValue to flow for TrainerData
    }

    fun nextReinforcementText(new: Int): String {
        return "$new is awesome!"
    }

    fun getHistory(): Pair<XYSeries, List<String>> {
        val history = _data.getHistory()
        val counts = history.map { it.howMany }
        val labels = history.map { "${it.at.day}-${it.at.month}" }
        val vals = SimpleXYSeries(counts, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sit-ups")
        return Pair(vals, labels)
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
