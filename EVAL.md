# CONTEXT

## Objectif de ce fichier

Ce fichier sert de contexte de reference pour un agent IA charge de :

- comprendre le cadre pedagogique de l'evaluation
- verifier si le projet KMP est conforme aux attentes du formateur
- evaluer la conformite du code a la grille EDUXIM
- prioriser les corrections qui augmentent le plus la defensabilite du rendu

Ce document fusionne deux sources :

- le preambule de l'epreuve fourni par le formateur
- la grille EDUXIM contenue dans `Grille-evaluation-di3-p3---evaluation-mobile-Eduxim.xlsx`
- le sujet fonctionnel contenu dans `evaluations.md`

## Sujet d'application a prendre en compte

L'evaluation porte sur la realisation d'une application `Kotlin Multiplatform` basee sur la `Rick and Morty API`, centree sur les `locations`.

Le sujet impose :

- un `listing des locations`
- un acces au `detail d'une location`
- sur `mobile`, une navigation `liste -> detail`
- sur `Desktop`, une presentation `master-detail` sur un seul ecran
- un support d'au minimum `2 plateformes`, avec `Android` et `Desktop` comme combinaison recommandee

Ce point est important pour l'evaluation :

- un projet techniquement propre mais hors sujet fonctionnel n'est pas conforme
- un projet dans le sujet mais mal structure reste sous-evalue

La conformite doit donc etre jugee a la fois sur :

- le respect du sujet fonctionnel impose
- la qualite de l'architecture
- l'alignement avec la grille EDUXIM

## Contraintes fonctionnelles obligatoires du sujet

Le minimum fonctionnel attendu est :

- sur `mobile` : un ecran `LocationList`
- sur `mobile` : un ecran `LocationDetail`
- une navigation entre ces deux ecrans
- sur `Desktop` : une vue unique avec liste a gauche et detail a droite
- un chargement de la liste des locations depuis l'API
- un chargement du detail d'une location a partir de son identifiant

Le detail doit au minimum exploiter :

- `name`
- `type`
- `dimension`
- une information derivee ou exploitee depuis `residents`

Un agent doit donc verifier non seulement l'existence de couches, mais aussi la couverture de ce parcours fonctionnel impose.

## Contraintes techniques obligatoires du sujet

Le sujet impose explicitement la demonstration des points suivants :

- initialisation d'un projet `KMP`
- configuration des dependances necessaires
- mise en place de `2 ecrans` minimum selon une logique `UDF / MVI`
- navigation `listing -> detail` sur `mobile`
- affichage `master-detail` sur `Desktop`
- creation d'au moins un `Model` metier et d'un `Repository` dans le `Domain`
- implementation d'un `Repository` dans la couche `Data`
- utilisation de `2 sources de donnees` pour demontrer un mecanisme de `fetch`
- usage de commentaires cibles sur les zones complexes
- mise en oeuvre d'une `fonction d'extension de Context`
- mise en place d'un `Manager` permettant de jouer un son
- demonstration du mecanisme `expect / actual`

Ces elements doivent etre consideres comme des preuves a rechercher en priorite pendant une revue.

## Decoupage pedagogique attendu par le sujet

Le sujet introduit cinq blocs dependants entre eux :

1. `Mise en place`
2. `Domain`
3. `Presentation`
4. `Data`
5. `Cross-Native`

Leur relation attendue est :

- `Mise en place` pose le socle
- `Domain` constitue le coeur
- `Data` et `Presentation` se branchent sur le `Domain`
- `Cross-Native` specialise ce qui doit l'etre selon la plateforme

Un agent doit penaliser les implementations qui inversent cette logique, par exemple :

- UI pilotee sans vrai `Domain`
- Data trop technique remontant dans le `Domain`
- cross-native injecte au mauvais niveau architectural

## Consequence pratique pour l'evaluation

Le sujet ne demande pas "beaucoup de features".
Il demande un parcours simple mais complet et defendable autour de `locations -> detail`.

Un agent doit donc valoriser :

- la pertinence du parcours principal
- la qualite de la separation des couches
- la lisibilite du `Data flow`
- la clarte de l'adaptation entre mobile et Desktop
- la pertinence du cross-platform

Un agent doit au contraire devaloriser :

- les features annexes qui diluent le sujet
- la complexite gratuite
- les integrations techniques non reliees au besoin principal

## Regles d'or de l'epreuve

### 1. Le build est non negociable

- Le projet doit compiler.
- L'application doit pouvoir etre lancee dans des conditions normales d'evaluation.
- Si une fonctionnalite n'est pas terminee, il vaut mieux fournir un mock, un stub ou une version partielle propre qu'un projet casse.

### 2. La qualite prime sur la completion totale

Le formateur n'attend pas forcement un sujet 100% termine. Il evalue surtout :

- la proprete de la mise en oeuvre
- la clarte de l'architecture
- la lisibilite du code
- la coherence des blocs produits
- la capacite a laisser un projet defendable et repris facilement

### 3. La grille de competences est le vrai referentiel

Le sujet sert de support. La grille EDUXIM sert de systeme d'evaluation.

Tout arbitrage technique doit donc viser la demonstration explicite d'un ou plusieurs criteres.

### 4. Pas de plagiat

Interdit :

- copier-coller massif sans appropriation
- reprise brute d'un ancien projet
- solution integree sans comprehension ni adaptation

Autorise :

- reutilisation de patterns attendus
- structure classique de clean architecture
- conventions normales de nommage, fichiers et composants

### 5. Les commentaires ne valent que s'ils sont utiles

Les commentaires doivent expliquer :

- une intention
- un arbitrage
- un comportement complexe

Les commentaires decoratifs, automatiques ou non relus n'apportent pas de points.

### 6. Le savoir-etre technique est visible dans le depot

Le formateur observe aussi :

- la proprete du repository
- la sobriete des commentaires
- la qualite du decoupage
- la clarte du nommage
- la capacite a extraire un composant quand c'est justifie
- la DX laissee au prochain developpeur

## Strategie attendue pendant l'epreuve

Quand une partie n'est pas finie :

- preferer un bloc partiel mais structurable et explicable
- ne pas laisser une zone critique dans un etat casse
- rendre visibles les intentions architecturales

Priorites recommandees :

1. garantir un build stable
2. poser une architecture defendable
3. produire quelques blocs propres, complets et coherents
4. documenter juste assez pour faciliter la reprise
5. etendre seulement ensuite

## Point d'attention majeur sur l'UI

Le preambule insiste explicitement sur les criteres UI suivants :

- `CRIT-DMA-D3-S-2`
- `CRIT-DMA-D3-SF-2`
- `CRIT-DMA-D3-SE-2`

En pratique, un agent doit donc considerer la couche Presentation/UI comme prioritaire, au meme niveau que la stabilite du build.

Le sujet `evaluations.md` renforce encore cette priorite en imposant explicitement :

- deux ecrans mobiles
- une navigation mobile
- une adaptation Desktop `master-detail`
- un modele `UDF / MVI`

## Comment utiliser ce contexte pour evaluer le projet

Procedure recommandee pour un agent :

1. verifier d'abord que le projet build
2. verifier ensuite la structure globale du repo et des modules
3. evaluer chaque couche selon les criteres EDUXIM observables
4. relever les ecarts bloquants, puis les ecarts de qualite
5. proposer des corrections dans l'ordre du meilleur ratio points / temps / risque

Important :

- Les criteres de type `S` mesurent surtout la comprehension theorique. Un agent ne peut les inferer qu'indirectement via le code, les noms, la documentation, l'architecture et la capacite a justifier les choix.
- Les criteres `SE` et `SF` sont en grande partie observables dans le depot.

## Echelle EDUXIM

La grille utilise principalement ces niveaux :

- `0.00` : Travail non realise
- `1.00` : Insuffisant
- `2.00` : En cours d'acquisition
- `2.50`, `3.00`, `3.20` : paliers intermediaires sur l'UI
- `4.00` : Maitrise

Quand un agent juge le projet, il doit expliciter :

- ce qui est observable dans le code
- le niveau plausible par critere
- les preuves utilisees
- ce qui manque pour atteindre `Acquis` ou `Maitrise`

## Synthese de la grille EDUXIM

## Bloc 1 - KMP et Clean Architecture

### `CRIT-DMA-D3-S-1`
Connaissances theoriques sur la clean architecture avec Kotlin Multiplatform.

Ce qu'il faut pouvoir demontrer :

- comprehension des source sets KMP (`commonMain`, `androidMain`, `iosMain`, etc.)
- architecture en couches
- decouplage des responsabilites
- usage du mecanisme `expect/actual` quand pertinent

Indices observables dans le code :

- organisation claire des packages ou modules
- code partage place dans `commonMain`
- specificites plateforme isolees dans les source sets dedies
- absence de melange fort entre UI, domaine et data

Pour viser `Acquis` ou mieux :

- structure KMP propre et lisible
- responsabilites separees
- mecanisme multiplateforme justifie

### `CRIT-DMA-D3-SE-1`
Structuration et qualite du code dans un projet KMP.

Preuves attendues :

- repo ordonne
- conventions de nommage coherentes
- `README.md` utile
- architecture presentable rapidement a un tiers
- dependances gerees proprement dans Gradle

Signaux de maitrise :

- README d'architecture clair
- couches nettes
- packages coherents
- experience de reprise amelioree pour le correcteur

### `CRIT-DMA-D3-SF-1`
Integration dans une architecture applicative orientee clean architecture.

Preuves attendues :

- separation Presentation / Domain / Data
- faible couplage
- dependances dirigees dans le bon sens
- injection de dependances si necessaire
- exemple d'adaptation multiplateforme, idealement `expect/actual`

Signaux de maitrise :

- architecture en couches vraiment appliquee
- integration propre de Gradle et des dependances
- usage defendable de l'injection de dependances
- exemple concret d'implementation specifique plateforme

## Bloc 2 - Presentation Layer, UDF, Compose, navigation

### `CRIT-DMA-D3-S-2`
Connaissances theoriques sur l'architecture UI avec MVI et Jetpack Compose.

Ce qu'il faut comprendre et pouvoir justifier :

- roles de `Screen`, `ViewModel`, `UiState`, `UiAction`, `Navigator`
- flux unidirectionnel des donnees
- gestion d'etat previsible
- organisation des ecrans
- interet architectural du pattern UDF/MVI

Indices observables :

- etats explicites
- actions explicites
- absence de logique metier dans les composables
- navigation non dispersee dans toute l'application

### `CRIT-DMA-D3-SE-2`
Qualite de code et amelioration de la DX avec UDF et Jetpack Compose.

Points de maitrise cites par la grille :

- navigation bien structuree et commentee
- noms d'ecrans explicites
- `ViewModel` limite a la logique metier et aux transitions d'etat
- `Screen` limite au rendu
- composables reutilisables et modulaires
- actions nommees clairement
- `UiState` minimal et centre sur la representation
- au moins un composant complexe documente en KDoc

Red flags :

- gros fichier UI monolithique
- logique metier dans les composables
- noms ambigus
- duplication forte
- absence totale de documentation utile

### `CRIT-DMA-D3-SF-2`
Application et integration dans une architecture UI avec MVI et Jetpack Compose.

Points de maitrise cites par la grille :

- une seule Activity pour l'application
- navigation centralisee via un composant `Navigator`
- Design System identifiable
- distinction claire entre `Screen.kt` et `ViewModel.kt`
- composants UI sans logique metier parasite

Pour juger ce critere, l'agent doit verifier en priorite :

- si l'app suit une philosophie single-activity
- si les ecrans sont clairement identifies
- si le flux `event -> state -> render` est lisible
- si la navigation est centralisee
- si un design system ou au moins une base de composants reutilisables existe

## Bloc 3 - Domain Layer

### `CRIT-DMA-D2-S-3`
Connaissances theoriques sur l'architecture de la couche Domain.

Attendus :

- Domain Models independants
- interfaces de repository dans le Domain
- separation nette entre logique metier et couches externes

### `CRIT-DMA-D2-SE-3`
Ameliorer l'experience de developpement de la couche Domain.

Attendus observables :

- composants metier bien nommes
- code indifferenciable par rapport a l'existant
- logique metier isolee des couches UI/Data
- commentaires de header uniquement quand ils expliquent une logique metier non triviale

### `CRIT-DMA-D2-SF-3`
Integration et adaptation a une architecture Domain proposee.

Attendus observables :

- use cases ou logique metier dedies
- models metier propres
- interfaces de repository dans le Domain
- aucune dependance du Domain vers la UI ou le Data concret

## Bloc 4 - Data Layer

### `CRIT-DMA-D2-S-2`
Connaissances theoriques sur l'architecture de la couche Data.

Attendus :

- comprehension des couples `Service/DTO` et `DAO/Entity`
- comprehension du fetch local/distant
- mappings entre representations
- respect du transit des donnees entre couches

### `CRIT-DMA-D2-SE-1`
Ameliorer l'experience de developpement de la couche Data.

Attendus observables :

- fonctions bien decoupees
- faible duplication
- commentaires utiles selon les conventions Kotlin
- noms explicites
- au moins un KDoc pertinent sur un comportement complexe

### `CRIT-DMA-D2-SF-4`
Integration et adaptation a une architecture Data proposee.

Attendus observables :

- couples `DAO/Entity` et `Service/DTO` clairement separes
- mecanisme de fetch coherent
- mapping implemente proprement
- code maintenable et modulaire

## Checklist de conformite rapide

Un agent IA peut utiliser cette checklist pour juger rapidement le projet.

### Gate 1 - Livrable minimum acceptable

- le projet compile
- l'application peut etre lancee
- le repo n'est pas laisse dans un etat incoherent
- le code ajoute est compris et defendable
- le sujet `Rick and Morty Locations` est effectivement respecte

### Gate 2 - Structure generale

- `README.md` explique au moins le lancement et l'architecture
- l'arborescence reflete les couches
- les noms de packages et de fichiers sont coherents
- les dependances Gradle sont propres et justifiables

### Gate 3 - Couche UI

- une seule Activity
- ecrans clairement identifies
- navigation centralisee
- `UiState` et `UiAction` explicites
- `ViewModel` separe du rendu
- composables reutilisables
- design system visible ou amorce
- sur mobile : parcours `liste -> detail`
- sur Desktop : presentation `master-detail`

### Gate 4 - Couche Domain

- models metier distincts
- repository interfaces dans le domain
- logique metier independante
- pas de dependance du domain vers les details externes

### Gate 5 - Couche Data

- data sources isolees
- DTO / Entity / mappers bien separes si la complexite le justifie
- repository implementations dans data
- strategie de fetch claire
- deux sources de donnees reellement mobilisees

### Gate 6 - DX et proprete

- commentaires rares mais utiles
- pas de duplication flagrante
- composants extraits quand necessaire
- code lisible sans explication orale permanente

## Ordre de priorite si le temps manque

Si un agent doit arbitrer sous contrainte de temps, prioriser :

1. build stable
2. respect du parcours fonctionnel `locations -> detail`
3. single activity + architecture UI lisible
4. separation Presentation / Domain / Data
5. README d'architecture
6. navigation centralisee et adaptation Desktop `master-detail`
7. `UiState` / `UiAction` / `ViewModel` propres
8. repositories et models metier
9. data layer avec deux sources, fetch et mappings
10. `expect/actual`, `Context` extension et audio manager si le besoin multiplateforme est reel et defendable

## Ce qu'un agent doit eviter

- ajouter de la complexite purement cosmetique
- multiplier les commentaires inutiles
- sur-architecturer sans besoin lisible
- laisser du code mort ou des fichiers brouillons
- melanger la logique metier avec les composables
- melanger le Domain avec les details techniques du Data
- casser le build pour "montrer plus de choses"

## Situation actuelle du repo

Constat initial au moment de la creation de ce fichier :

- le projet est un template Kotlin Multiplatform / Compose Multiplatform de depart
- les cibles visibles sont surtout `android` et `jvm`
- la structure actuelle ne demontre pas encore une clean architecture complete
- le `README.md` present est un README de template, pas un README d'architecture d'evaluation
- la couche UI actuelle ressemble encore a un exemple Compose minimal
- le sujet fonctionnel `Rick and Morty Locations` n'est pas encore implemente
- les contraintes `2 sources de donnees`, `master-detail Desktop`, `audio manager`, `Context` extension et `expect/actual` ne sont pas encore demontrees

Conclusion pratique :

Un agent ne doit pas considerer l'etat actuel comme conforme a la grille. Il doit s'en servir comme base technique a faire evoluer vers un projet d'evaluation defendable.

## Points d'inspection concrets dans ce repo

Fichiers et zones a auditer en priorite :

- `README.md`
- `settings.gradle.kts`
- `build.gradle.kts`
- `composeApp/build.gradle.kts`
- `composeApp/src/commonMain`
- `composeApp/src/androidMain`
- `composeApp/src/jvmMain`
- les futurs packages `presentation`, `domain`, `data`, `designsystem`, `navigation`, `di`

## Format de verdict recommande pour un agent

Quand un agent evalue la conformite du projet, il devrait repondre avec :

1. un verdict global : `non conforme`, `partiellement conforme`, `globalement conforme`
2. les blocages critiques
3. les criteres EDUXIM deja demontres
4. les criteres partiellement demontres
5. les actions prioritaires pour augmenter le niveau
6. les risques si on livre en l'etat

## Resume ultra-court

Le projet doit surtout etre :

- buildable
- propre
- structure
- defendable
- aligne sur la grille EDUXIM

La completion fonctionnelle totale est secondaire par rapport a la qualite architecturale, la lisibilite du code, la clarte de l'UI en UDF/Compose, et la separation propre des couches `Presentation`, `Domain` et `Data`.
