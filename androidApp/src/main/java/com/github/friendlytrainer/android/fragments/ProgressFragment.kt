package com.github.friendlytrainer.android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
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
        val formatter = LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null)
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
    }
}