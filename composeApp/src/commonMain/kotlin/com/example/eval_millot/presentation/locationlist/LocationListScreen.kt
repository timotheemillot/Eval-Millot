package com.example.eval_millot.presentation.locationlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
// Choisit le contenu liste qui correspond a l'etat actuel de l'ecran.
fun LocationListScreen(
    state: LocationListUiState,
    onAction: (LocationListAction) -> Unit,
    modifier: Modifier = Modifier,
    selectedLocationId: Int? = null,
) {
    when {
        state.isLoading -> LoadingContent(modifier = modifier)
        state.errorMessage != null -> ErrorContent(
            message = state.errorMessage,
            onRetry = { onAction(LocationListAction.RetryClicked) },
            modifier = modifier,
        )

        else -> LocationsContent(
            locations = state.locations,
            selectedLocationId = selectedLocationId,
            onLocationClicked = { locationId ->
                onAction(LocationListAction.LocationClicked(locationId))
            },
            modifier = modifier,
        )
    }
}

@Composable
// Affiche un retour de chargement pendant l'execution de la requete de liste.
private fun LoadingContent(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
// Affiche la derniere erreur de liste avec une action de relance.
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier,
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
// Affiche la liste des locations selectionnables.
private fun LocationsContent(
    locations: List<LocationListItemUiModel>,
    selectedLocationId: Int?,
    onLocationClicked: (Int) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "Rick and Morty Locations",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        items(
            items = locations,
            key = LocationListItemUiModel::id,
        ) { location ->
            LocationCard(
                location = location,
                isSelected = location.id == selectedLocationId,
                onClick = { onLocationClicked(location.id) },
            )
        }
    }
}

@Composable
// Affiche une ligne et met en evidence la selection sur ordinateur.
private fun LocationCard(
    location: LocationListItemUiModel,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = location.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = location.typeLabel,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = location.dimensionLabel,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = location.residentsLabel,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
