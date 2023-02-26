package com.example.machinelearning

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.machinelearning.databinding.FragmentTextTransBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class TextTransFragment : Fragment() {
    private var _binding: FragmentTextTransBinding? = null
    private val binding: FragmentTextTransBinding
        get() = _binding!!
    private var originalText: String = ""
    lateinit var pDialog: SweetAlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextTransBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTranslate.setOnClickListener {
            setUpProgressDialog()
            originalText = binding.etEnteredText.text.toString()
            if (originalText == "") {
                Toast.makeText(requireContext(), "Please enter the text man", Toast.LENGTH_SHORT)
                    .show()
            } else {
                prepareTranslateModel()
            }
        }
    }

    private fun setUpProgressDialog() {
        pDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun prepareTranslateModel() {
        val options: TranslatorOptions =
            TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.HINDI)
                .build()
        val englishHindiTranslator = Translation.getClient(options)
        pDialog.titleText = "Model Downloading..."
        pDialog.show()
        englishHindiTranslator.downloadModelIfNeeded().addOnSuccessListener {
            pDialog.dismissWithAnimation()
            translateText(englishHindiTranslator)
        }.addOnFailureListener {
            binding.txtTransResult.text = "Some Error Occurred"
        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun translateText(englishHindiTranslator: com.google.mlkit.nl.translate.Translator) {
        pDialog.titleText = "Translate Text"
        pDialog.show()
        englishHindiTranslator.translate(originalText).addOnSuccessListener {
            pDialog.dismissWithAnimation()
            binding.txtTransResult.text = it
        }.addOnFailureListener {
            binding.txtTransResult.text = "some error occurred"
        }
    }

}