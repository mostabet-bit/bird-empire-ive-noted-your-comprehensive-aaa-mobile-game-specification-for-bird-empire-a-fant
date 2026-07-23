package com.birdempire.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.birdempire.data.models.BirdEntity

@Composable
fun BirdCollectionScreen(
    birds: List<BirdEntity>,
    onBirdClick: (BirdEntity) -> Unit
) {
    var selectedRarity by remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                "My Birds (${birds.size})",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Rarity Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("ALL", "COMMON", "RARE", "EPIC", "LEGENDARY").forEach { rarity ->
                FilterChip(
                    selected = selectedRarity == rarity,
                    onClick = { selectedRarity = rarity },
                    label = { Text(rarity) }
                )
            }
        }

        // Birds Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val filteredBirds = if (selectedRarity == "ALL") birds 
                else birds.filter { it.rarity == selectedRarity }

            items(filteredBirds.size) { index ->
                BirdCard(
                    bird = filteredBirds[index],
                    onClick = { onBirdClick(filteredBirds[index]) }
                )
            }
        }
    }
}

@Composable
fun BirdCard(
    bird: BirdEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Bird Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🦅", fontSize = androidx.compose.ui.unit.sp(48))
            }

            // Bird Info
            Text(
                bird.name,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Lv. ${bird.level}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    bird.rarity,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (bird.rarity) {
                        "LEGENDARY" -> MaterialTheme.colorScheme.error
                        "EPIC" -> MaterialTheme.colorScheme.tertiary
                        "RARE" -> MaterialTheme.colorScheme.secondary
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            }

            // Health Bar
            LinearProgressIndicator(
                progress = bird.health / 100f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
