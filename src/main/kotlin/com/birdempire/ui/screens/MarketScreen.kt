package com.birdempire.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.birdempire.data.models.MarketListingEntity

@Composable
fun MarketScreen(
    listings: List<MarketListingEntity>,
    onListingClick: (MarketListingEntity) -> Unit,
    onCreateListing: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("ALL") }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Market",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Button(onClick = onCreateListing) {
                    Text("Sell")
                }
            }
        }

        // Search and Filter
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search listings...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Category Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("ALL", "EGGS", "BIRDS", "RESOURCES").forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }

        // Listings
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listings.size) { index ->
                MarketListingCard(
                    listing = listings[index],
                    onClick = { onListingClick(listings[index]) }
                )
            }
        }
    }
}

@Composable
fun MarketListingCard(
    listing: MarketListingEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = onClick,
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    listing.itemName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Qty: ${listing.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    "💰 ${listing.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    "by ${listing.sellerName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
