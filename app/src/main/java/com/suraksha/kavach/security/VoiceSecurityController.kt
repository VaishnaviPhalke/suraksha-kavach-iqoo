package com.suraksha.kavach.security

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

/**
 * VoiceSecurityController handles:
 * 1. Real on-device Android Text-To-Speech (TTS) synthesis for audible security alerts.
 * 2. Real on-device SpeechRecognizer for hands-free voice command interpretation ("Confirm", "Revoke").
 */
class VoiceSecurityController(
    private val context: Context,
    private val onVoiceCommandRecognized: (command: String) -> Unit = {}
) {

    private var textToSpeech: TextToSpeech? = null
    private var isTtsReady: Boolean = false
    private var speechRecognizer: SpeechRecognizer? = null

    init {
        initializeTts()
    }

    private fun initializeTts() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.let { tts ->
                    val result = tts.setLanguage(Locale.US)
                    if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                        isTtsReady = true
                        tts.setSpeechRate(0.95f)
                        tts.setPitch(1.0f)
                    }
                }
            }
        }

        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.d("VoiceSecurity", "TTS started: $utteranceId")
            }

            override fun onDone(utteranceId: String?) {
                Log.d("VoiceSecurity", "TTS completed: $utteranceId")
            }

            override fun onError(utteranceId: String?) {
                Log.e("VoiceSecurity", "TTS error on utterance: $utteranceId")
            }
        })
    }

    /**
     * Speaks the challenge prompt aloud through the device's audio hardware.
     */
    fun speakChallengePrompt(
        prompt: String = "Unusual access detected. Confirm or Revoke?",
        onDone: () -> Unit = {}
    ) {
        if (isTtsReady && textToSpeech != null) {
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SURAKSHA_CHALLENGE")
            textToSpeech?.speak(prompt, TextToSpeech.QUEUE_FLUSH, params, "SURAKSHA_CHALLENGE")
        }
        onDone()
    }

    /**
     * Listens for hands-free voice input ("Confirm" or "Revoke") using on-device SpeechRecognizer.
     */
    fun startListeningForVoiceCommand(
        onResult: (isConfirm: Boolean, rawText: String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onError("Speech recognition hardware not available on this device.")
            return
        }

        speechRecognizer?.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {
                    val message = when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH -> "No voice match detected. Please speak clearly."
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Voice timeout. No command detected."
                        else -> "Speech recognition code: $error"
                    }
                    onError(message)
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val spokenText = matches?.firstOrNull()?.lowercase() ?: ""
                    Log.d("VoiceSecurity", "Recognized voice command: $spokenText")

                    if (spokenText.contains("revoke") || spokenText.contains("cancel") || spokenText.contains("lock") || spokenText.contains("purge")) {
                        onResult(false, spokenText)
                        onVoiceCommandRecognized("REVOKE")
                    } else if (spokenText.contains("confirm") || spokenText.contains("allow") || spokenText.contains("yes") || spokenText.contains("pass")) {
                        onResult(true, spokenText)
                        onVoiceCommandRecognized("CONFIRM")
                    } else {
                        onError("Unrecognized command: \"$spokenText\". Expected 'Confirm' or 'Revoke'.")
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US.toString())
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Say 'Confirm' to approve or 'Revoke' to lock.")
        }

        speechRecognizer?.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer?.stopListening()
        speechRecognizer?.destroy()
        speechRecognizer = null
    }

    fun shutdown() {
        stopListening()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isTtsReady = false
    }
}
