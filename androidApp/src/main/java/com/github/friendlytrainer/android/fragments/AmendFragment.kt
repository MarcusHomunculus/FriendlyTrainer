package com.github.friendlytrainer.android.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.android.databinding.AmendFragmentBinding
import com.github.friendlytrainer.android.viewmodels.MainViewModel

class AmendFragment : Fragment() {
    private val sharedModel: MainViewModel by activityViewModels()
    private lateinit var binding: AmendFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = AmendFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = sharedModel
        binding.lifecycleOwner = this
        sharedModel.reinforcementText.observe(viewLifecycleOwner, Observer<String> { new ->
            context?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Super")
                builder.setMessage(new)
                builder.setCancelable(true)
                builder.setPositiveButton("okay") { _, _ -> }
                val dialog = builder.create()
                dialog.show()
                // TODO: replace by style (eg. https://stackoverflow.com/a/42373688)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.text))
            } })
    }
}