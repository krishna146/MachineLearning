package com.example.machinelearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.machinelearning.databinding.FragmentChatBotBinding
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.example.machinelearning.databinding.FragmentTextTransBinding

class TextTransFragment : Fragment() {
    private var _binding: FragmentTextTransBinding? = null
    private val binding: FragmentTextTransBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextTransBinding.inflate(inflater, container, false)
        return binding.root
    }

}