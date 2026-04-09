package com.example.eval_millot.presentation.locationdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
// Choisit le contenu detail qui correspond a l'etat actuel de l'ecran.
fun LocationDetailScreen(
    state: LocationDetailUiState,
    onAction: (LocationDetailAction) -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
) {
    when {
        state.isLoading -> LoadingContent(
            modifier = modifier,
            showBackButton = showBackButton,
            onAction = onAction,
        )

        state.errorMessage != null -> ErrorContent(
            message = state.errorMessage,
            onRetry = { onAction(LocationDetailAction.RetryClicked) },
            onBack = { onAction(LocationDetailAction.BackClicked) },
            modifier = modifier,
            showBackButton = showBackButton,
        )

        state.detail != null -> DetailContent(
            detail = state.detail,
            onBack = { onAction(LocationDetailAction.BackClicked) },
            modifier = modifier,
            showBackButton = showBackButton,
        )

        else -> PlaceholderContent(
            message = state.placeholderMessage ?: "Aucun detail a afficher.",
            modifier = modifier,
        )
    }
}

@Composable
// Affiche un retour de chargement et garde la navigation retour disponible sur mobile.
private fun LoadingContent(
    modifier: Modifier,
    showBackButton: Boolean,
    onAction: (LocationDetailAction) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (showBackButton) {
                Button(onClick = { onAction(LocationDetailAction.BackClicked) }) {
                    Text("Back")
                }
            }
            CircularProgressIndicator()
        }
    }
}

@Composable
// Affiche une erreur de chargement du detail avec relance et retour optionnel.
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier,
    showBackButton: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (showBackButton) {
                Button(onClick = onBack) {
                    Text("Back")
                }
            }
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(onClick = onRetry) {
                Text("Reessayer")
            }
        }
    }
}

@Composable
// Affiche les champs de la location selectionnee dans le volet de detail.
private fun DetailContent(
    detail: LocationDetailContentUiModel,
    onBack: () -> Unit,
    modifier: Modifier,
    showBackButton: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (showBackButton) {
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        Text(
            text = detail.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = detail.typeLabel,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = detail.dimensionLabel,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = detail.residentsLabel,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
// Remplit la zone de detail avant toute selection de location.
private fun PlaceholderContent(
    message: String,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
