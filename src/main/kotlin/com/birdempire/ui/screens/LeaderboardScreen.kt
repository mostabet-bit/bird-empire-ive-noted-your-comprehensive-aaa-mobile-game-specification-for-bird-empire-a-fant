package com.birdempire.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.birdempire.data.models.LeaderboardEntry

@Composable
fun LeaderboardScreen(
    entries: List<LeaderboardEntry>,
    currentPlayerRank: Int
) {
    var selectedTab by remember { mutableStateOf(0) }

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
                "Leaderboard",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Level") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Birds") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Wealth") }
            )
        }

        // Your Rank
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Your Rank: #$currentPlayerRank",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "🏆",
                    fontSize = androidx.compose.ui.unit.sp(24)
                )
            }
        }

        // Rankings List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(entries.size) { index ->
                LeaderboardEntryCard(
                    entry = entries[index],
                    rank = index + 1
                )
            }
        }
    }
}

@Composable
fun LeaderboardEntryCard(
    entry: LeaderboardEntry,
    rank: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Rank
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = when (rank) {
                            1 -> MaterialTheme.colorScheme.secondary
                            2 -> MaterialTheme.colorScheme.tertiary
                            3 -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surface
                        },
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    when (rank) {
                        1 -> "🥇"
                        2 -> "🥈"
                        3 -> "🥉"
                        else -> rank.toString()
                    },
                    fontSize = androidx.compose.ui.unit.sp(18)
                )
            }

            // Player Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    entry.playerName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Level ${entry.level}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Score
            Text(
                entry.score.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
