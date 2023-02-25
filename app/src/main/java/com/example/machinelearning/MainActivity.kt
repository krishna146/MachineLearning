package com.example.machinelearning

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.machinelearning.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var inputImage: InputImage
    lateinit var imageLabeler: ImageLabeler
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), it);
        binding.imgSelected.setImageBitmap(photo)
        inputImage = InputImage.fromBitmap(photo, 0)
        processImage()
    }

    private fun processImage() {
        imageLabeler.process(inputImage).addOnSuccessListener { labels ->
            var result = ""
            for (label in labels) {
                result = result + "\n" + label.text
            }
            binding.txtResult.text = result
        }.addOnFailureListener {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        // This is a comment.. -- Aman Kumar
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        imageLabeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        setContentView(binding.root)
        binding.btnChoosePicture.setOnClickListener {
            contract.launch("image/*")
        }

    }

}