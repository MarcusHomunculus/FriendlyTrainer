package com.github.friendlytrainer.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androidplot.ui.HorizontalPositioning
import com.androidplot.ui.VerticalPositioning
import com.androidplot.util.PixelUtils
import com.androidplot.xy.*
import com.github.friendlytrainer.Constants
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.android.databinding.ProgressFragmentBinding
import com.github.friendlytrainer.android.viewmodels.MainViewModel
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition

class ProgressFragment : Fragment() {
    private val _sharedModel: MainViewModel by activityViewModels()
    private lateinit var _binding: ProgressFragmentBinding
    private lateinit var _plot: XYPlot

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = ProgressFragmentBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        _plot = fragmentBinding.root.findViewById(R.id.progress_graph)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.viewmodel = _sharedModel
        _binding.lifecycleOwner = this
        _sharedModel.state.observe(viewLifecycleOwner) { newState ->
            if (newState.progress.visibility == View.VISIBLE)
                draw(_plot, _sharedModel.getHistory())
        }
}

    private fun draw(canvas: XYPlot, what: Pair<XYSeries, List<String>>) {
        if (what.first.size() < Constants.MIN_SAMPLES) {
            Toast.makeText(activity, "Not enough data to show!", Toast.LENGTH_LONG).show()
            _sharedModel.focus(MainViewModel.InfoView.AMEND)
            return
        }
        canvas.clear()
        val formatter = LineAndPointFormatter(Color.RED, Color.GREEN, Color.TRANSPARENT, null)
        canvas.addSeries(what.first, formatter)
        canvas.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object: Format() {
            override fun format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition): StringBuffer {
                val idx = (obj as Number).toFloat().toInt()
                return toAppendTo.append(what.second[idx])
            }

            override fun parseObject(source: String, pos: ParsePosition): Any? {
                return null
            }
        }
        canvas.legend.isVisible = false
        canvas.setDomainStep(StepMode.INCREMENT_BY_VAL, 1.0)
        canvas.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0)
        canvas.domainTitle.position(
            PixelUtils.dpToPix(50.0F), HorizontalPositioning.ABSOLUTE_FROM_RIGHT,
            PixelUtils.dpToPix(25.0F), VerticalPositioning.ABSOLUTE_FROM_BOTTOM)
        val (min, max) = what.first.minMax(1, null)
        canvas.setRangeBoundaries(min - 1, BoundaryMode.FIXED, max + 1, BoundaryMode.FIXED)
    }

    private fun XYSeries.minMax(absoluteMin: Int? = null, absoluteMax: Int? = null): Pair<Int, Int> {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (i in 0 until this.size()) {
            if (this.getY(i).toInt() < min)
                min = this.getY(i).toInt()
            else if (this.getY(i).toInt() > max)
                max = this.getY(i).toInt()
        }
        if (absoluteMin != null && min < absoluteMin)
            min = absoluteMin
        if (absoluteMax != null && max > absoluteMax)
            max = absoluteMax
        return Pair(min, max)
    }
}