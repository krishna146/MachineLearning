package com.example.machinelearning

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.machinelearning.databinding.FragmentChatBotBinding
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.example.machinelearning.databinding.FragmentLanguageDetectionBinding
import com.example.machinelearning.util.TAG
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier


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
        detectLanguage("how are you")
    }

    private fun detectLanguage(langText: String) {
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(langText)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    Toast.makeText(requireContext(), "Can't identify language.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Language: $languageCode", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "some error occurred", Toast.LENGTH_SHORT)
                    .show()
            }

    }
}
