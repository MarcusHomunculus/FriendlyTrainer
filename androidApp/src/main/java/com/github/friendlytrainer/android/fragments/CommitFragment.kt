package com.github.friendlytrainer.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.android.databinding.CommitFragmentBinding
import com.github.friendlytrainer.android.factory.MainViewModelFactory
import com.github.friendlytrainer.android.mixins.SharedDataStoring
import com.github.friendlytrainer.android.viewmodels.MainViewModel

class CommitFragment : Fragment(), SharedDataStoring {
    private val sharedModel: MainViewModel by viewModels {
        MainViewModelFactory(deriveSharedDatabaseHandle(activity!!.applicationContext))
    }
    private lateinit var binding: CommitFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = CommitFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = sharedModel
        binding.lifecycleOwner = this
        sharedModel.reinforcementText.observe(viewLifecycleOwner) { new ->
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
            } }
    }
}