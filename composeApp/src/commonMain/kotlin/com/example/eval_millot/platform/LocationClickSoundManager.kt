package com.example.eval_millot.platform

// Joue un retour sonore quand une location est sélectionnée sur la plateforme courante.
interface LocationClickSoundManager {
// Émet le son de sélection si la plateforme le permet.
    fun play()

// Libère les ressources sonores natives quand l'application a fini de les utiliser.
    fun release()
}
