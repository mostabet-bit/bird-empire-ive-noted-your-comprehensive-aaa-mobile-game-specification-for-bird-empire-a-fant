package com.birdempire.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    playerLevel: Int,
    playerXP: Long,
    coins: Long,
    gems: Long,
    skyCrystals: Long,
    onNavigateToIsland: () -> Unit,
    onNavigateToBirds: () -> Unit,
    onNavigateToMarket: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLeaderboard: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        onNavigateToIsland()
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Island") },
                    label = { Text("Island") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        onNavigateToBirds()
                    },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Birds") },
                    label = { Text("Birds") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onNavigateToMarket()
                    },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Market") },
                    label = { Text("Market") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        onNavigateToLeaderboard()
                    },
                    icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Leaderboard") },
                    label = { Text("Leaderboard") }
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = {
                        selectedTab = 4
                        onNavigateToProfile()
                    },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Level $playerLevel", style = MaterialTheme.typography.titleMedium)
                            LinearProgressIndicator(
                                progress = (playerXP % 1000) / 1000f,
                                modifier = Modifier.width(100.dp)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ResourceBadge("💰", coins.toString())
                            ResourceBadge("💎", gems.toString())
                            ResourceBadge("✨", skyCrystals.toString())
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Content based on selected tab
            when (selectedTab) {
                0 -> Text("Island Screen")
                1 -> Text("Birds Screen")
                2 -> Text("Market Screen")
                3 -> Text("Leaderboard Screen")
                4 -> Text("Profile Screen")
            }
        }
    }
}

@Composable
fun ResourceBadge(icon: String, value: String) {
    Card(
        modifier = Modifier
            .height(32.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = androidx.compose.ui.unit.sp(16))
            Text(value, style = MaterialTheme.typography.labelSmall)
        }
    }
}
