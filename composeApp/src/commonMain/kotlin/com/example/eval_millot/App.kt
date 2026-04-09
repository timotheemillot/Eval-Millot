package com.example.eval_millot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eval_millot.navigation.LocationDestination
import com.example.eval_millot.navigation.LocationNavigator
import com.example.eval_millot.platform.LocationClickSoundManager
import com.example.eval_millot.presentation.locationdetail.LocationDetailAction
import com.example.eval_millot.presentation.locationdetail.LocationDetailScreen
import com.example.eval_millot.presentation.locationdetail.LocationDetailViewModel
import com.example.eval_millot.presentation.locationlist.LocationListAction
import com.example.eval_millot.presentation.locationlist.LocationListScreen
import com.example.eval_millot.presentation.locationlist.LocationListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

// Choisit l'adaptation de mise en page partagee pour la plateforme courante.
enum class AppLayoutMode {
    Mobile,
    Desktop,
}

@Composable
// Heberge la coquille partagee de l'application et synchronise les flux liste/detail.
fun App(layoutMode: AppLayoutMode) {
    KoinContext {
        MaterialTheme {
            val navigator = remember { LocationNavigator() }
            val listViewModel = koinViewModel<LocationListViewModel>()
            val detailViewModel = koinViewModel<LocationDetailViewModel>()
            val soundManager = koinInject<LocationClickSoundManager>()

            LaunchedEffect(listViewModel) {
                listViewModel.onAction(LocationListAction.LoadRequested)
            }

            LaunchedEffect(navigator, detailViewModel) {
                navigator.destination.collectLatest { destination ->
                    when (destination) {
                        LocationDestination.List -> {
                            detailViewModel.onAction(LocationDetailAction.ClearRequested)
                        }

                        is LocationDestination.Detail -> {
                            detailViewModel.onAction(
                                LocationDetailAction.LoadRequested(destination.locationId),
                            )
                        }
                    }
                }
            }

            when (layoutMode) {
                AppLayoutMode.Mobile -> MobileLocationsApp(
                    navigator = navigator,
                    listViewModel = listViewModel,
                    detailViewModel = detailViewModel,
                    soundManager = soundManager,
                )

                AppLayoutMode.Desktop -> DesktopLocationsApp(
                    navigator = navigator,
                    listViewModel = listViewModel,
                    detailViewModel = detailViewModel,
                    soundManager = soundManager,
                )
            }
        }
    }
}

@Composable
// Affiche un ecran mobile a la fois selon la destination courante.
private fun MobileLocationsApp(
    navigator: LocationNavigator,
    listViewModel: LocationListViewModel,
    detailViewModel: LocationDetailViewModel,
    soundManager: LocationClickSoundManager,
) {
    val destination = rememberDestination(navigator)
    val listState = listViewModel.uiState.collectAsState().value
    val detailState = detailViewModel.uiState.collectAsState().value

    when (destination) {
        LocationDestination.List -> LocationListScreen(
            state = listState,
            onAction = { action ->
                handleListAction(
                    action = action,
                    listViewModel = listViewModel,
                    navigator = navigator,
                    soundManager = soundManager,
                )
            },
        )

        is LocationDestination.Detail -> LocationDetailScreen(
            state = detailState,
            onAction = { action ->
                handleDetailAction(
                    action = action,
                    detailViewModel = detailViewModel,
                    navigator = navigator,
                )
            },
            showBackButton = true,
        )
    }
}

@Composable
// Affiche la vue maitre-detail sur ordinateur a partir des ViewModel partages.
private fun DesktopLocationsApp(
    navigator: LocationNavigator,
    listViewModel: LocationListViewModel,
    detailViewModel: LocationDetailViewModel,
    soundManager: LocationClickSoundManager,
) {
    val destination = rememberDestination(navigator)
    val selectedLocationId = (destination as? LocationDestination.Detail)?.locationId
    val listState = listViewModel.uiState.collectAsState().value
    val detailState = detailViewModel.uiState.collectAsState().value

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Surface(
            modifier = Modifier
                .weight(0.95f)
                .fillMaxHeight(),
            tonalElevation = 2.dp,
        ) {
            LocationListScreen(
                state = listState,
                onAction = { action ->
                    handleListAction(
                        action = action,
                        listViewModel = listViewModel,
                        navigator = navigator,
                        soundManager = soundManager,
                    )
                },
                selectedLocationId = selectedLocationId,
            )
        }

        Surface(
            modifier = Modifier
                .weight(1.05f)
                .fillMaxHeight(),
            tonalElevation = 2.dp,
        ) {
            LocationDetailScreen(
                state = detailState,
                onAction = { action ->
                    handleDetailAction(
                        action = action,
                        detailViewModel = detailViewModel,
                        navigator = navigator,
                    )
                },
            )
        }
    }
}

@Composable
// Lit la destination du navigateur comme etat Compose.
private fun rememberDestination(navigator: LocationNavigator): LocationDestination {
    return navigator.destination.collectAsState().value
}

// Dirige les actions de liste vers le chargement, le son ou la navigation.
private fun handleListAction(
    action: LocationListAction,
    listViewModel: LocationListViewModel,
    navigator: LocationNavigator,
    soundManager: LocationClickSoundManager,
) {
    when (action) {
        LocationListAction.LoadRequested,
        LocationListAction.RetryClicked -> listViewModel.onAction(action)

        is LocationListAction.LocationClicked -> {
            soundManager.play()
            navigator.openDetail(action.locationId)
        }
    }
}

// Dirige les actions de detail vers le ViewModel ou le retour arriere.
private fun handleDetailAction(
    action: LocationDetailAction,
    detailViewModel: LocationDetailViewModel,
    navigator: LocationNavigator,
) {
    when (action) {
        is LocationDetailAction.LoadRequested,
        LocationDetailAction.RetryClicked,
        LocationDetailAction.ClearRequested -> detailViewModel.onAction(action)

        LocationDetailAction.BackClicked -> navigator.openList()
    }
}
