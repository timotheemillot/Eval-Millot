# TEACHING

## Objectif

Ce fichier rattache l'implémentation actuelle aux critères EDUXIM et aide à préparer une explication orale défendable. Il ne documente que ce qui est concrètement implémenté dans le dépôt à l'instant T.

## Points de la grille implémentés

### Critère

- `CRIT-DMA-D3-S-1` - Connaissances théoriques sur la clean architecture avec Kotlin Multiplatform
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

Le dépôt montre une vraie séparation KMP, des source sets clairs et des spécialisations spécifiques à la plateforme qui restent en dehors du Domain partagé. Le code partagé porte le flux de l'application, tandis qu'Android et Desktop fournissent chacun leurs points d'entrée et leurs implémentations réelles là où c'est nécessaire.

### Où c'est implémenté

- `composeApp/build.gradle.kts`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/network/PlatformHttpClient.kt`
- `composeApp/src/androidMain/kotlin/com/example/eval_millot/network/PlatformHttpClient.android.kt`
- `composeApp/src/jvmMain/kotlin/com/example/eval_millot/network/PlatformHttpClient.jvm.kt`
- `composeApp/src/androidMain/kotlin/com/example/eval_millot/platform/AndroidLocationClickSoundManager.kt`
- `composeApp/src/androidMain/kotlin/com/example/eval_millot/platform/ContextSoundExtensions.kt`
- `composeApp/src/jvmMain/kotlin/com/example/eval_millot/platform/DesktopLocationClickSoundManager.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/App.kt`

### Comment cela fonctionne

Le code commun déclare les contrats partagés et le flux UI. Les fichiers spécifiques à la plateforme implémentent le client HTTP et le gestionnaire sonore pour chaque cible, de sorte que le reste de l'application ne dépend pas des détails Android ou Desktop.

### Notes de défense

Si on te le demande, explique que `expect / actual` n'est pas décoratif ici : cela résout de vraies différences de plateforme tout en gardant la base de code partagée lisible et testable.

### Écart restant

La configuration KMP est déjà défendable pour Android et Desktop. La limite principale est simplement que le sujet ne demande pas de cibles supplémentaires, donc la preuve multi-plateforme reste volontairement centrée.

### Critère

- `CRIT-DMA-D3-SE-1` - Structuration et qualité du code dans un projet KMP
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

Le README est désormais factuel et décrit l'état réel du projet, les commandes de lancement et l'architecture. L'arborescence du dépôt est aussi facile à parcourir, avec des packages regroupés par responsabilité.

### Où c'est implémenté

- `README.md`
- `composeApp/build.gradle.kts`
- `settings.gradle.kts`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/`

### Comment cela fonctionne

Le README dit maintenant la vérité sur l'application actuelle au lieu d'un état de modèle : il décrit les couches présentes, les flux mobile et Desktop, les éléments natifs partagés et les commandes de build et d'exécution.

### Notes de défense

C'est facile à justifier en évaluation parce que l'évaluateur peut ouvrir le README, exécuter les commandes, puis vérifier la même structure dans l'arborescence source.

### Écart restant

La documentation est désormais utile, mais elle reste volontairement concise. Elle ne remplace pas un déroulé de l'architecture pendant l'oral.

### Critère

- `CRIT-DMA-D3-SF-1` - Intégration dans une architecture applicative orientée clean architecture
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

Le câblage de l'application suit une direction clean architecture : Presentation dépend du Domain, Data dépend du Domain, et les détails spécifiques à la plateforme restent dans la zone d'intégration. `Koin` construit le graphe à partir de modules partagés et de modules de plateforme, ce qui rend les dépendances faciles à expliquer.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/di/KoinInit.kt`
- `composeApp/src/androidMain/kotlin/com/example/eval_millot/di/PlatformModule.android.kt`
- `composeApp/src/jvmMain/kotlin/com/example/eval_millot/di/PlatformModule.jvm.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationSummary.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationDetail.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationsRepository.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImpl.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/platform/LocationClickSoundManager.kt`

### Comment cela fonctionne

`KoinInit` déclare les dépendances partagées, tandis que les modules de plateforme fournissent le client HTTP, le gestionnaire sonore et les spécialisations natives. L'implémentation du repository reste dans Data, les contrats restent dans Domain, et les comportements spécifiques à la plateforme sont isolés derrière des interfaces dédiées.

### Notes de défense

Si on te le demande, décris le sens des dépendances de l'extérieur vers l'intérieur : l'UI parle aux `ViewModel`, les `ViewModel` parlent au contrat du Domain, et Data fournit l'implémentation.

### Écart restant

Le câblage utilise désormais `Koin`, ce qui rend la DI plus explicite et plus proche du projet de référence. Le point de vigilance restant est surtout de garder des modules simples et proportionnés au périmètre de l'application.

### Critère

- `CRIT-DMA-D3-S-2` - Connaissances théoriques sur l'architecture de la couche UI avec MVI et Jetpack Compose
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

L'UI expose des objets d'état explicites et des actions explicites. Les écrans rendent l'état et émettent des événements, tandis que les `ViewModel` gèrent le chargement et la logique de transition. La navigation est sortie des composables.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationlist/LocationListUiState.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationlist/LocationListAction.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationlist/LocationListViewModel.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationlist/LocationListScreen.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailUiState.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailAction.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailViewModel.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailScreen.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/navigation/LocationNavigator.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/App.kt`

### Comment cela fonctionne

`LocationListScreen` et `LocationDetailScreen` consomment l'état UI et renvoient des actions. `LocationNavigator` garde la destination courante comme état UI partagé, et `App.kt` route les clics de liste vers la navigation au lieu d'enterrer cette logique dans l'arbre de rendu.

### Notes de défense

Si on te le demande, présente le flux comme `UiAction -> ViewModel -> UiState -> Screen`. Le `ViewModel` de détail protège aussi contre les requêtes obsolètes, ce qui montre que la machine d'état est volontairement pensée.

### Écart restant

L'architecture est claire, mais l'UI bénéficierait encore d'un système de design plus marqué si le périmètre s'agrandit.

### Critère

- `CRIT-DMA-D3-SE-2` - Qualité de code et amélioration de la DX avec UDF et Jetpack Compose
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

L'UI est découpée en petits fichiers aux noms explicites, les écrans restent ciblés, et la partie détail possède un test dédié qui prouve que le `ViewModel` résiste aux mises à jour obsolètes. Cela rend le code plus facile à lire et à défendre.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationlist/LocationListScreen.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailScreen.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailViewModel.kt`
- `composeApp/src/commonTest/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailViewModelTest.kt`

### Comment cela fonctionne

Chaque écran sépare le rendu de l'orchestration. Les helpers internes isolent les états de chargement, d'erreur, de remplacement et de contenu, ce qui garde les points d'entrée compacts et lisibles.

### Notes de défense

C'est facile à défendre comme UI de style UDF : état en entrée, actions en sortie, aucun accès au repository dans les composables, et un test qui prouve que le flux de détail gère l'ordre des requêtes en sécurité.

### Écart restant

Il n'existe toujours pas de package de design system réutilisable, donc la couche visuelle est propre mais pas encore fortement identifiée.

### Critère

- `CRIT-DMA-D3-SF-2` - Application et intégration dans une architecture UI avec MVI et Jetpack Compose
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

L'application utilise une racine de composition unique, un `LocationNavigator` central, et des slices de présentation séparées pour la liste et le détail. Le mobile utilise une navigation liste vers détail, tandis que le Desktop conserve le même flux dans une mise en page maître-détail sur un seul écran.

### Où c'est implémenté

- `composeApp/src/androidMain/kotlin/com/example/eval_millot/MainActivity.kt`
- `composeApp/src/jvmMain/kotlin/com/example/eval_millot/main.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/App.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/navigation/LocationNavigator.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationlist/LocationListScreen.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/presentation/locationdetail/LocationDetailScreen.kt`

### Comment cela fonctionne

`EvalMillotApplication` et `main.kt` démarrent `Koin`, puis `MainActivity` et la fenêtre Desktop lancent `App()`. Dans `App.kt`, le mobile alterne entre les écrans selon le navigateur, tandis que le Desktop affiche la liste et le détail côte à côte en partageant le même état de destination.

### Notes de défense

Si on te le demande, insiste sur le fait que la navigation est centralisée au lieu d'être dispersée dans l'UI. Mentionne aussi que l'expérience Desktop n'est pas une application différente, mais le même flux adapté à un écran maître-détail unique.

### Écart restant

La structure est solide, mais l'UI bénéficierait encore d'un système de design plus fort et de davantage d'aides à la composition au niveau des écrans si le périmètre s'agrandit.

### Critère

- `CRIT-DMA-D2-S-3` - Connaissances théoriques sur l'architecture de la couche Domain
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

La couche Domain contient des modèles orientés métier et un contrat de repository qui ne dépend ni de Compose, ni de Ktor, ni des types Android ou Desktop.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationSummary.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationDetail.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationsRepository.kt`

### Comment cela fonctionne

`LocationSummary` et `LocationDetail` contiennent les valeurs dont l'application a besoin pour afficher la liste et le détail sélectionné. `LocationsRepository` exprime les cas d'usage sans exposer de détails techniques d'API ou de plateforme.

### Notes de défense

Si on te le demande, explique que le Domain est volontairement petit mais propre : il définit ce dont l'application a besoin, pas la façon dont les données sont récupérées.

### Écart restant

Le Domain est déjà défendable pour le périmètre actuel. Une extension future pourrait ajouter des cas d'usage plus riches, mais ce n'est pas nécessaire pour justifier l'implémentation présente.

### Critère

- `CRIT-DMA-D2-SE-3` - Améliorer l'expérience de développement de la couche Domain
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

Le code du Domain est concis, bien nommé et isolé des détails UI ou data. Les modèles expriment directement le sens métier, ce qui rend la couche facile à lire et à réutiliser pendant l'oral.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationSummary.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationDetail.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/domain/locations/LocationsRepository.kt`

### Comment cela fonctionne

Il n'y a aucune lourdeur technique dans la couche Domain. Les fichiers sont courts, le nommage est explicite, et le contrat du repository est la seule frontière de comportement que les autres couches doivent respecter.

### Notes de défense

Le Domain est facile à justifier parce qu'il reste indépendant et lisible. L'évaluateur peut le parcourir rapidement et voir qu'il n'est ni couplé au format API ni à Compose.

### Écart restant

Il y a encore de la place pour ajouter davantage d'opérations métier plus tard, mais la couche actuelle est déjà suffisamment propre pour être défendue.

### Critère

- `CRIT-DMA-D2-S-2` - Connaissances théoriques sur l'architecture de la couche Data
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

La couche Data montre maintenant une source distante, une seconde source en mémoire, un parsing explicite et un repository qui orchestre récupération et cache. Le contrat distant renvoie des DTO techniques au lieu de laisser fuiter des modèles du Domain, donc la séparation est matériellement plus propre qu'avant.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/LocationApi.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/HttpLocationApi.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/LocationResponseParser.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/RemoteLocationDto.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/local/LocationCacheDataSource.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/local/InMemoryLocationCacheDataSource.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/local/CachedLocationModels.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImpl.kt`
- `composeApp/src/commonTest/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImplTest.kt`

### Comment cela fonctionne

`HttpLocationApi` récupère le JSON brut et l'analyse en `RemoteLocationDto`. `LocationsRepositoryImpl` lit d'abord le cache, se rabat sur l'API du domaine si nécessaire, puis transforme les modèles techniques en modèles du Domain et stocke la copie en cache pour une réutilisation ultérieure.

### Notes de défense

Le point important à dire est qu'il y a deux vraies sources, pas une seconde voie artificielle. Le test de liste en priorité cache et les tests de détail prouvent que le repository choisit réellement entre comportement en cache et comportement distant.

### Écart restant

La seconde source est encore uniquement en mémoire, donc la persistance n'est pas encore démontrée. C'est acceptable pour le périmètre actuel, mais c'est la faiblesse restante la plus claire dans Data.

### Critère

- `CRIT-DMA-D2-SE-1` - Améliorer l'expérience de développement de la couche Data
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

Le code Data est découpé en petites fonctions, modèles explicites et KDoc ciblé sur la stratégie de récupération du détail. Les tests du repository figent aussi le comportement attendu de type cache-first.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImpl.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/LocationResponseParser.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/local/CachedLocationModels.kt`
- `composeApp/src/commonTest/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImplTest.kt`

### Comment cela fonctionne

Le repository garde les règles de récupération à un seul endroit, le parser garde le traitement JSON séparé, et les helpers de mapping rendent les transformations visibles au lieu de les cacher en ligne. Le KDoc sur le chemin de détail explique pourquoi il suit la même stratégie de priorité au cache que la liste.

### Notes de défense

Si on te demande pourquoi le commentaire existe, explique qu'il documente un choix d'orchestration non trivial : le flux de détail utilise volontairement la même stratégie à deux sources que le flux de liste.

### Écart restant

La couche est lisible et testable, mais le cache reste uniquement en mémoire et non persistant. Cela garde le périmètre simple, sans être faux.

### Critère

- `CRIT-DMA-D2-SF-4` - Intégration et adaptation à une architecture Data proposée
- état de l'implémentation : `implemented`

### Pourquoi ce point compte

Les DTO restent dans Data, le parser est explicite, et l'implémentation du repository combine les chemins de cache et de récupération distante avant de renvoyer les modèles du Domain. La frontière entre modèles techniques et modèles métier est maintenant claire dans le code.

### Où c'est implémenté

- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/RemoteLocationDto.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/remote/LocationResponseParser.kt`
- `composeApp/src/commonMain/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImpl.kt`
- `composeApp/src/commonTest/kotlin/com/example/eval_millot/data/locations/LocationsRepositoryImplTest.kt`

### Comment cela fonctionne

`RemoteLocationDto` représente la charge utile de l'API, `parseRemoteLocations` et `parseRemoteLocation` extraient les valeurs depuis le JSON, et le repository décide s'il faut utiliser le cache ou récupérer depuis l'endpoint distant avant de mapper vers le Domain.

### Notes de défense

Si on te le demande, explique que la frontière de mapping est désormais entièrement dans Data. C'est la preuve architecturale importante : le Domain ne voit plus les modèles techniques distants.

### Écart restant

Le repository est cohérent, mais la seconde source reste uniquement en mémoire. Un cache persistant serait l'étape suivante si un discours Data plus solide devenait nécessaire.

## Points notables manquants

- La seconde source dans Data reste uniquement en mémoire ; il n'existe pas encore de stockage local persistant.
- La couche Domain est volontairement petite, mais il n'existe toujours pas de classe de cas d'usage explicite au-delà du contrat de repository.
- L'UI est propre, mais il n'existe toujours pas de package de design system dédié.

## Priorités de revue

1. Si l'on veut une preuve Data plus forte, remplacer ou compléter le cache en mémoire par une source locale persistante.
2. Si l'on veut une meilleure défendabilité UI, extraire un petit package de design system pour les composants et les tokens réutilisables.
3. Si l'on veut une meilleure pédagogie Domain, ajouter une couche de cas d'usage dédiée au-dessus du contrat de repository.
4. Conserver en l'état le flux mobile liste/détail et le câblage Desktop maître-détail.
5. Continuer à documenter uniquement ce qui est directement visible dans le code.
