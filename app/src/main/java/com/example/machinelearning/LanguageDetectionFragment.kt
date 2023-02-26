package com.example.machinelearning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.machinelearning.databinding.FragmentLanguageDetectionBinding
import com.example.machinelearning.util.TAG
import com.google.mlkit.nl.languageid.LanguageIdentification
import java.util.*


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
        binding.btnDetectLanguage.setOnClickListener {
            val text = binding.etEnterText.text.toString()
            detectLanguage(text)
        }
    }

    private fun detectLanguage(langText: String) {
        val languageIdentifier = LanguageIdentification.getClient()
        var textViewText: String = ""
        languageIdentifier.identifyLanguage(langText)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    binding.txtDetectedLang.text = "Can't detect Language"
                } else {
                    val loc = Locale(languageCode)
                    val name = loc.getDisplayLanguage(loc)
                    binding.txtDetectedLang.text = name
                }
            }
            .addOnFailureListener {
                binding.txtDetectedLang.text = "Some Error Occurred"
            }

    }
}
