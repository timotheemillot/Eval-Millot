# Workflow D'Orchestration Des Agents

## Objectif

Ce document definit l'ordre logique d'appel des agents du projet et separe clairement leurs responsabilites.

Le but est de :

- eviter les recouvrements de role
- limiter les hallucinations
- proteger l'architecture
- garder un flux de travail lisible
- rendre chaque agent responsable d'une etape precise
- rendre l'orchestration executable avec de vrais sous-agents

## Principe general

Le workflow suit cette logique :

1. cadrer
2. concevoir
3. produire
4. verifier techniquement
5. verifier architecturalement
6. verifier pedagogiquement
7. documenter ce qui est reellement demontre

Chaque agent a un droit d'action limite.
Un agent ne doit pas "prendre le role" d'un autre.

Quand l'environnement permet les sous-agents, chaque phase doit etre executee par un sous-agent distinct correspondant au role cible.

Si les sous-agents ne sont pas disponibles, le workflow est considere comme degrade et cela doit etre dit explicitement dans la sortie.

## Agents disponibles

- `architecte-kmp`
- `codeur-kmp`
- `reviewer-kmp`
- `eval-reviewer`
- `eval-teacher`

## Regle obligatoire de sous-agents

Le mode nominal de ce projet est un workflow a sous-agents reels.

Cela signifie :

- `orchestrateur-kmp` choisit l'etape suivante
- un sous-agent specialise execute cette etape
- sa sortie devient l'entree de l'etape suivante

Un seul agent ne doit pas enchainer implicitement plusieurs roles si le systeme sait deleguer.

Exception admise :

- environnement sans support de sous-agents
- demande explicite de l'utilisateur de rester en mono-agent

Dans ces cas, il faut annoncer un "mode degrade" avant de continuer.

## Regle de separation des droits

### `architecte-kmp`

Droits :

- analyser la structure globale
- valider les choix KMP
- verifier l'alignement avec `CODEX.md`
- comparer l'architecture au projet de reference via le skill
- proposer des corrections structurelles

Limites :

- ne doit pas etre l'agent principal de production
- ne doit pas implementer de larges changements applicatifs par defaut
- ne doit pas faire la revue pedagogique finale

### `codeur-kmp`

Droits :

- produire le code
- creer les fichiers necessaires
- faire evoluer les couches `Presentation`, `Domain`, `Data`
- integrer le comportement KMP attendu
- realiser des slices fonctionnelles completes

Limites :

- ne decide pas seul de gros changements d'architecture si le sujet est ambigu
- ne valide pas lui-meme la coherence finale
- ne doit pas s'auto-attribuer une conformite d'evaluation

### `reviewer-kmp`

Droits :

- faire une revue technique generale
- detecter incoherences, hallucinations, faux raccords, code mort, wiring incomplet
- verifier qu'un comportement annonce existe vraiment

Limites :

- ne remplace pas l'architecte
- ne remplace pas le reviewer d'evaluation
- ne doit pas reorienter tout le projet pour des preferences personnelles

### `eval-reviewer`

Droits :

- juger la conformite au sujet et a l'evaluation
- verifier l'alignement avec la grille et les attentes du formateur
- prioriser les ecarts qui impactent la note

Limites :

- ne sert pas de reviewer technique generaliste
- ne doit pas rewriter l'architecture
- ne doit pas documenter pedagogiquement a la place de `eval-teacher`

### `eval-teacher`

Droits :

- identifier les points EDUXIM reellement implementes
- creer ou mettre a jour `TEACHING.md`
- expliquer ce qui est fait, ou, et comment le defendre

Limites :

- ne doit pas inventer des criteres implementes
- ne doit pas ecrire du code metier par defaut
- ne remplace pas la revue technique ni la revue d'architecture

## Workflow standard

## Phase 0 - Pre-flight

Verifier les documents de reference.

Fichiers attendus :

- `CODEX.md`
- `EVAL.md`
- les instructions d'agents dans `.codex/agents`
- les skills dans `.codex/skills`

Si un fichier de reference manque :

- ne pas lancer un cycle complet comme si tout etait clair
- noter le blocage
- corriger le cadre avant la production

Verifier egalement si le support de sous-agents est disponible.

Si oui :

- utiliser le workflow nominal a sous-agents

Si non :

- annoncer explicitement le mode degrade

## Phase 1 - Cadrage d'architecture

Agent :

- `architecte-kmp`

Objectif :

- verifier que la cible du prochain travail est saine architecturalement
- confirmer la bonne direction KMP
- verifier la coherence avec `CODEX.md`
- comparer le depot courant au projet exemple via le skill d'architecture

Sortie attendue :

- verdict d'architecture
- ecarts structurels majeurs
- plus petite direction correcte pour la prochaine implementation
- conclusion explicite sur ce qu'il faut adopter, adapter partiellement, ou refuser du projet exemple

Quand appeler cette phase :

- au debut d'un nouveau bloc
- avant un gros refactor
- avant de toucher a la structure des couches
- avant d'introduire un mecanisme KMP important (`expect / actual`, audio manager, DI, navigation)
- avant d'introduire ou de refondre `Koin`
- avant de migrer une couche `Presenter` vers `ViewModel`

Regle obligatoire :

- cette phase doit utiliser le skill `kmp-reference-architecture`
- l'architecte doit inspecter le projet exemple avant de donner une direction structurelle
- sans cette verification, la phase d'architecture est incomplete

## Phase 2 - Production

Agent :

- `codeur-kmp`

Objectif :

- implementer une slice coherente du projet

Exemples de slices :

- modele de domaine + repository contract
- repository data + deux sources + mappers
- liste mobile + etat + actions + ViewModel
- detail mobile + navigation
- ecran Desktop master-detail
- integration `Koin` + modules communs / plateforme
- integration cross-native audio

Regles :

- une slice doit etre livrable et defendable
- le codeur ne doit pas lancer plusieurs chantiers mal relies
- si une partie est incomplete, elle doit etre stubbee proprement
- le codeur s'appuie sur la sortie precedente de `architecte-kmp`
- si la direction d'architecture n'est pas claire, il renvoie vers `architecte-kmp` avant d'implementer

Sortie attendue :

- code modifie
- resume de ce qui a ete implemente
- points restants clairs

## Phase 3 - Revue technique generale

Agent :

- `reviewer-kmp`

Objectif :

- verifier que le travail du codeur est reel, coherent, raccorde, et sans hallucination

Focus :

- symboles existants
- wiring reel
- coherence entre interfaces et implementations
- imports valides
- flux de donnees reels
- absence de faux "done"

Sortie attendue :

- findings techniques
- hallucinations suspectees
- niveau de coherence general
- correctifs minimaux a appliquer

Regle :

- si `reviewer-kmp` remonte un `critical`, on retourne au `codeur-kmp`

## Phase 4 - Revue d'architecture

Agent :

- `architecte-kmp`

Objectif :

- verifier que les modifications techniques n'ont pas degrade l'architecture globale

Focus :

- couche correcte
- source set correct
- separation `Presentation / Domain / Data`
- integrations natives a la bonne place
- alignement avec la structure cible
- fidelite a la direction issue du skill de reference

Sortie attendue :

- verdict d'architecture mis a jour
- mismatches avec `CODEX.md`
- prochaines corrections structurelles si necessaire

Regle :

- si l'architecture est `incorrecte` ou `fragile` avec blocage fort, retour au `codeur-kmp`
- si la comparaison avec le projet exemple n'a pas ete faite ou est trop superficielle, la revue d'architecture n'est pas validee

## Phase 5 - Revue d'evaluation

Agent :

- `eval-reviewer`

Objectif :

- verifier si le travail produit aide vraiment pour la note

Focus :

- respect du sujet
- adequation avec les criteres EDUXIM
- qualite visible pour le correcteur
- impact pedagogique reel des changements

Sortie attendue :

- verdict de conformite
- criteres renforces
- criteres encore faibles
- meilleures prochaines actions pour la note

Regle :

- cette phase ne remplace pas la revue technique
- elle intervient apres la coherence technique et architecturale

## Phase 6 - Documentation pedagogique

Agent :

- `eval-teacher`

Objectif :

- transformer le code reellement implemente en materiel explicable

Action :

- creer ou mettre a jour `TEACHING.md`

Contenu attendu :

- point de grille implemente
- ou c'est implemente
- comment ca fonctionne
- quoi dire a l'oral
- ce qui manque encore

Regle :

- `eval-teacher` n'intervient qu'apres une implementation jugee suffisamment reelle
- ne jamais documenter un point non prouve dans le code

## Ordre logique complet

Ordre nominal :

1. `architecte-kmp`
2. `codeur-kmp`
3. `reviewer-kmp`
4. `architecte-kmp`
5. `eval-reviewer`
6. `eval-teacher`

Execution nominale :

1. `orchestrateur-kmp` choisit et lance `architecte-kmp`
2. `orchestrateur-kmp` transmet la sortie a `codeur-kmp`
3. `orchestrateur-kmp` transmet ensuite a `reviewer-kmp`
4. `orchestrateur-kmp` relance `architecte-kmp` si la structure a bouge
5. `orchestrateur-kmp` transmet a `eval-reviewer`
6. `orchestrateur-kmp` transmet a `eval-teacher`

## Boucle de correction

Si un probleme est detecte :

- probleme de code ou de wiring :
  retour a `codeur-kmp`
- probleme d'architecture :
  passage par `architecte-kmp`, puis retour a `codeur-kmp`
- probleme de conformite evaluation :
  retour a `codeur-kmp` avec priorites donnees par `eval-reviewer`
- absence de preuve pedagogique :
  pas de faux positif, `eval-teacher` documente seulement le reel

## Workflow par type de demande

## Cas 1 - Nouveau bloc fonctionnel

Exemple :

- liste des locations
- detail d'une location
- navigation mobile

Ordre :

1. `architecte-kmp`
2. `codeur-kmp`
3. `reviewer-kmp`
4. `eval-reviewer`
5. `eval-teacher`

## Cas 2 - Refactor d'architecture

Exemple :

- redecoupage en packages
- introduction `Koin`
- refonte navigation
- separation Domain/Data
- migration `Presenter -> ViewModel`

Ordre :

1. `architecte-kmp`
2. `codeur-kmp`
3. `architecte-kmp`
4. `reviewer-kmp`
5. `eval-reviewer`

Ici, `architecte-kmp` doit repasser avant la revue pedagogique.

## Cas 3 - Bug ou incoherence

Ordre :

1. `reviewer-kmp`
2. `codeur-kmp`
3. `reviewer-kmp`
4. si impact structurel : `architecte-kmp`

## Cas 4 - Verification avant rendu

Ordre :

1. `reviewer-kmp`
2. `architecte-kmp`
3. `eval-reviewer`
4. `eval-teacher`

Objectif :

- s'assurer que le code est coherent
- que l'architecture tient
- que la conformite d'evaluation est defendable
- que `TEACHING.md` permet de justifier les points implementes

## Regles d'or d'orchestration

- un seul agent produit du code par defaut : `codeur-kmp`
- les autres agents lisent, critiquent, cadrent ou documentent
- ne pas faire produire du code par plusieurs agents en parallele sans coordination forte
- utiliser de vrais sous-agents plutot qu'une simple simulation narrative quand le systeme le permet
- ne pas demander a `eval-reviewer` de faire une revue technique profonde a la place de `reviewer-kmp`
- ne pas demander a `architecte-kmp` de noter la grille a la place de `eval-reviewer`
- ne pas demander a `eval-teacher` de deduire des points non visibles
- ne pas sauter le skill `kmp-reference-architecture` pour une decision structurelle
- ne pas reintroduire des presenters ou du wiring manuel si `CODEX.md` cible `ViewModel + Koin`

## Signal de fin de cycle

Un cycle est considere sain quand :

- `reviewer-kmp` ne voit pas de probleme majeur
- `architecte-kmp` juge l'architecture `coherente` ou `solide`
- `eval-reviewer` juge le travail au moins `partiellement conforme`
- `eval-teacher` peut documenter des points reels dans `TEACHING.md`

## Resume ultra-court

Workflow recommande :

1. `architecte-kmp` cadre
2. `codeur-kmp` implemente
3. `reviewer-kmp` verifie la realite technique
4. `architecte-kmp` revalide la structure
5. `eval-reviewer` juge la valeur pour la note
6. `eval-teacher` documente ce qui est reellement demontre

Et, en mode nominal, chacune de ces etapes est realisee par un sous-agent dedie.
