# EvalMillot

Application Kotlin Multiplatform centree sur le sujet d'evaluation **Rick and Morty Locations**.

## Etat du depot

Le depot contient aujourd'hui une base fonctionnelle KMP pour Android et Desktop, avec une architecture deja decoupee en couches et des adaptations plateforme visibles dans les source sets.

Le build `:composeApp:build` est vert au moment de cette mise a jour.

## Ce qui est present

- couche `Presentation` avec ecrans, `UiState`, `Action` et `ViewModel` pour la liste et le detail
- couche `Domain` avec les modeles metier et le contrat de repository
- couche `Data` avec une `LocationApi` distante, une source locale en memoire, les mappers et l'implementation du repository
- injection de dependances avec `Koin`
- navigation mobile centralisee entre liste et detail
- adaptation Desktop en `master-detail` sur un seul ecran
- mecanisme `expect / actual` pour le client HTTP multiplateforme
- manager de son declenche au clic sur une location
- extension `Context` cote Android pour construire ce manager

## Direction d'architecture appliquee

Le depot utilise maintenant une couche `Presentation` basee sur des `ViewModel` et une injection de dependances basee sur `Koin`, dans l'esprit du projet exemple de reference.

Direction retenue :

- remplacer les presenters par des `ViewModel`
- remplacer le cablage manuel principal par des modules `Koin`
- garder les frontieres `Presentation / Domain / Data` visibles
- laisser les dependances de plateforme dans les source sets de plateforme

## Structure observee

- `composeApp/src/commonMain`
  - `presentation`
  - `domain`
  - `data`
  - `navigation`
  - `di`
  - `network`
  - `platform`
- `composeApp/src/androidMain`
  - `EvalMillotApplication`
  - `di`
  - `network`
  - `platform`
  - `MainActivity`
- `composeApp/src/jvmMain`
  - `di`
  - `network`
  - `platform`

## Fonctionnement actuel

### Mobile

Le flux mobile affiche la liste des locations, puis ouvre l'ecran de detail lorsqu'une location est selectionnee.

### Desktop

Le flux Desktop affiche une vue `master-detail` avec la liste a gauche et le detail a droite.

### Donnees

Le repository de `Data` orchestre une `LocationApi` distante et une source locale en memoire. Le code presente aussi des mappers explicites entre les modeles techniques et les modeles metier.

### DI et presentation

`Koin` demarre au lancement sur Android et Desktop, fournit le repository partage, le client HTTP, le manager de son et les `ViewModel` d'ecran. L'UI recupere ensuite ces `ViewModel` via Koin au lieu de construire un conteneur manuel.

### Cross-native

Le projet utilise un `expect / actual` pour le client HTTP. Le son de clic est porte par un `LocationClickSoundManager`, avec une implementation Android et une implementation Desktop, et la construction Android passe par une extension de `Context`.

## Lancement

### Android

```powershell
.\gradlew.bat :composeApp:assembleDebug
```

### Desktop

```powershell
.\gradlew.bat :composeApp:run
```

### Verification

```powershell
.\gradlew.bat :composeApp:build
```

Cette verification est actuellement verte.

## Limites restantes

- ce README decrit l'etat reel du depot, mais ne remplace pas une revue du comportement a l'execution sur Android et Desktop
- la portee fonctionnelle reste centree sur `locations -> detail`; les extensions eventuelles doivent rester compatibles avec cette base
- la valeur pedagogique depend encore de la qualite des implementations concretes dans les couches `Presentation`, `Domain`, `Data` et `Cross-Native`
