package com.github.friendlytrainer.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.friendlytrainer.android.R
import com.github.friendlytrainer.android.databinding.AmendFragmentBinding
import com.github.friendlytrainer.android.viewmodels.MainViewModel

class AmendFragment : Fragment() {
    private val sharedModel: MainViewModel by activityViewModels()
    private var binding: AmendFragmentBinding? = null

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
        binding?.apply {
            viewmodel = sharedModel
        }
    }
}