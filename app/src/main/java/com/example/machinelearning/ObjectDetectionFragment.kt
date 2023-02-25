package com.example.machinelearning

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.example.machinelearning.databinding.FragmentObjectDetectionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions


class ObjectDetectionFragment : Fragment() {
    private var _binding: FragmentObjectDetectionBinding? = null
    private val binding: FragmentObjectDetectionBinding
        get() = _binding!!
    private lateinit var inputImage: InputImage
    private lateinit var imageLabeler: ImageLabeler
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val photo = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it);
        binding.imgSelected.setImageBitmap(photo)
        inputImage = InputImage.fromBitmap(photo, 0)
        processImage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentObjectDetectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLabeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        binding.btnChoosePicture.setOnClickListener {
            contract.launch("image/*")
        }
    }

    private fun processImage() {
        imageLabeler.process(inputImage).addOnSuccessListener { labels ->
            var result = ""
            for (label in labels) {
                result = result + label.text + "/"
            }
            binding.txtResult.visibility = View.VISIBLE
            binding.txtResult.text = result
        }.addOnFailureListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}