package com.example.machinelearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.machinelearning.databinding.FragmentChatBotBinding
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.example.machinelearning.databinding.FragmentLanguageDetectionBinding


class LanguageDetectionFragment : Fragment() {
    private var _binding: FragmentLanguageDetectionBinding? = null
    private val binding: FragmentLanguageDetectionBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLanguageDetectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}