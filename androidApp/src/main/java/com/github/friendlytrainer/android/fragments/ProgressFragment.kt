package com.github.friendlytrainer.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.friendlytrainer.android.databinding.ProgressFragmentBinding
import com.github.friendlytrainer.android.viewmodels.MainViewModel

class ProgressFragment : Fragment() {
    private val sharedModel: MainViewModel by activityViewModels()
    private lateinit var binding: ProgressFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = ProgressFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = sharedModel
        binding.lifecycleOwner = this
    }
}