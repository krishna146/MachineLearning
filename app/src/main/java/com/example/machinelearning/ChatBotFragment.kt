package com.example.machinelearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.machinelearning.databinding.FragmentChatBotBinding
import com.example.machinelearning.databinding.FragmentHomeBinding
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplyGenerator
import com.google.mlkit.nl.smartreply.SmartReplySuggestion
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

class ChatBotFragment : Fragment() {
    private var _binding: FragmentChatBotBinding? = null
    private val binding: FragmentChatBotBinding
        get() = _binding!!
    lateinit var conversation: ArrayList<TextMessage>
    lateinit var smartReplyGenerator: SmartReplyGenerator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversation = ArrayList()
        smartReplyGenerator = SmartReply.getClient()
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()
            if (message == "") {
                Toast.makeText(requireContext(), "Please enter the message", Toast.LENGTH_SHORT)
                    .show()
            } else {
                conversation.add(
                    TextMessage.createForRemoteUser(
                        message,
                        System.currentTimeMillis(),
                        "12345"
                    )
                )
                smartReplyGenerator.suggestReplies(conversation).addOnSuccessListener {
                    if (it.status == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        binding.txtReply.text = "NOT SUPPORTED LANGUAGE"
                    } else if (it.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        var reply = ""
                        for (suggestion: SmartReplySuggestion in it.suggestions) {
                            reply += suggestion.text + "\n"
                        }
                        binding.txtReply.text = reply
                    }
                }
                    .addOnFailureListener {
                        binding.txtReply.text = "Some Unexpected Error Occurred"
                    }
            }
        }
    }

}