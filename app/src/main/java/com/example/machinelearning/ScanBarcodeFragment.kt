package com.example.machinelearning

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.machinelearning.databinding.FragmentChatBotBinding
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.example.machinelearning.databinding.FragmentScanBarcodeBinding
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class ScanBarcodeFragment : Fragment() {
    private var _binding: FragmentScanBarcodeBinding? = null
    private val binding: FragmentScanBarcodeBinding
        get() = _binding!!
    lateinit var barcodeScanner: BarcodeScanner
    private lateinit var inputImage: InputImage
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val photo = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it);
        binding.imgBarCode.setImageBitmap(photo)
        inputImage = InputImage.fromBitmap(photo, 0)
        processQr()
    }

    private fun processQr() {
        binding.imgBarCode.visibility = View.GONE
        binding.txtCodeResult.visibility = View.VISIBLE
        barcodeScanner.process(inputImage).addOnSuccessListener {
            for (barcode: Barcode in it) {
                when (barcode.valueType) {
                    Barcode.TYPE_WIFI -> {
                        val ssid = barcode.wifi!!.ssid
                        val password = barcode.wifi!!.password
                        val type = barcode.wifi!!.encryptionType
                        binding.txtCodeResult.text =
                            "ssid: $ssid \n password: $password \n type: $type"
                    }
                    Barcode.TYPE_URL -> {
                        val title = barcode.url!!.title
                        val url = barcode.url!!.url
                        binding.txtCodeResult.text = "title: $title \n url: $url"
                    }
                    Barcode.TYPE_TEXT -> {
                        val data = barcode.displayValue
                        binding.txtCodeResult.text = "Result: $data"
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBarcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barcodeScanner = BarcodeScanning.getClient()
        binding.btnScanQr.setOnClickListener {
            contract.launch("image/*")
        }
    }


}