package com.birdempire.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.birdempire.data.models.PlayerProfile

@Composable
fun ProfileScreen(
    profile: PlayerProfile,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = androidx.compose.ui.unit.sp(60))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        profile.username,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        profile.islandName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        item {
            // Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard("Level", profile.level.toString())
                StatCard("Birds", profile.totalBirds.toString())
                StatCard("Buildings", profile.totalBuildings.toString())
            }
        }

        item {
            // Resources
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Resources",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ResourceRow("💰 Coins", profile.coins.toString())
                ResourceRow("💎 Gems", profile.gems.toString())
                ResourceRow("✨ Sky Crystals", profile.skyCrystals.toString())
            }
        }

        item {
            // Actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit Profile")
                }

                OutlinedButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                label,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ResourceRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
