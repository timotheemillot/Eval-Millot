package com.example.eval_millot.platform

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator

// Joue un bref bip système pour les sélections de location sur Android.
internal class AndroidLocationClickSoundManager(
    context: Context,
) : LocationClickSoundManager {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)

// Ignore le son lorsque l'appareil est en mode silencieux.
    override fun play() {
        if (audioManager?.ringerMode == AudioManager.RINGER_MODE_SILENT) {
            return
        }
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 120)
    }

// Libère le générateur de tonalité quand l'app n'en a plus besoin.
    override fun release() {
        toneGenerator.release()
    }
}
