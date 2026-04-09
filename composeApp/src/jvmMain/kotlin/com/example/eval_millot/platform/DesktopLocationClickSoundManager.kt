package com.example.eval_millot.platform

import java.awt.Toolkit

// Joue un simple bip pour les sélections de location sur le bureau.
private class DesktopLocationClickSoundManager : LocationClickSoundManager {
// Tente d'émettre le bip standard du toolkit sans faire échouer l'interface.
    override fun play() {
        runCatching { Toolkit.getDefaultToolkit().beep() }
    }

// Ne libère aucune ressource car le bip de bureau est sans état.
    override fun release() = Unit
}

// Crée le gestionnaire sonore de bureau utilisé par la coquille partagée.
fun createLocationClickSoundManager(): LocationClickSoundManager {
    return DesktopLocationClickSoundManager()
}
