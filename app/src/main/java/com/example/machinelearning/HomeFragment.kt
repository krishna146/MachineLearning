package com.example.machinelearning

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLangDetec.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_languageDetectionFragment)
        }
        binding.txtTransl.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_textTransFragment)
        }
        binding.btnChatBot.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_chatBotFragment)
        }
        binding.btnTxtFromImg.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_textImageFragment)
        }
        binding.btnScanBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scanBarcodeFragment)
        }
        binding.btnImgLabel.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_objectDetectionFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}