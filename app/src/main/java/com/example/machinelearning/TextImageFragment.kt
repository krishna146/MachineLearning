package com.example.machinelearning

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.machinelearning.databinding.FragmentChatBotBinding
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.example.machinelearning.databinding.FragmentTextImageBinding
import com.example.machinelearning.util.TAG
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextImageFragment : Fragment() {
    private var _binding: FragmentTextImageBinding? = null
    private val binding: FragmentTextImageBinding
        get() = _binding!!
    lateinit var textRecognizer: TextRecognizer
    private lateinit var inputImage: InputImage
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val photo = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it);
        binding.imgSelected.setImageBitmap(photo)
        convertImageToText(it)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        binding.btnChoosePicture.setOnClickListener {
            contract.launch("image/*")

        }
    }

    private fun convertImageToText(imgUri: Uri?) {
        try {
            inputImage = InputImage.fromFilePath(requireActivity().applicationContext, imgUri!!)
            textRecognizer.process(inputImage)
                .addOnSuccessListener {
                    binding.txtResult.visibility = View.VISIBLE
                    binding.txtResult.text = it.text
                }.addOnFailureListener {
                    binding.txtResult.visibility = View.VISIBLE
                    binding.txtResult.text = it.localizedMessage.toString()
                }
        } catch (e: Exception) {
        }

    }

}