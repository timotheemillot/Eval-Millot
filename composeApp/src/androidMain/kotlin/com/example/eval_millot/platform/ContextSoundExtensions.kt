package com.example.eval_millot.platform

import android.content.Context

// Construit le gestionnaire sonore Android à partir d'un contexte applicatif sûr.
fun Context.createLocationClickSoundManager(): LocationClickSoundManager {
    return AndroidLocationClickSoundManager(applicationContext)
}
