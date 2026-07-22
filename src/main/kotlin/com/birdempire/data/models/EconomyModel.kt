package com.birdempire.data.models

import androidx.room.*
import kotlinx.serialization.Serializable

enum class CurrencyType {
    COINS, GEMS, SKY_CRYSTALS
}

enum class ResourceType {
    COINS, GEMS, SKY_CRYSTALS, EGGS, GOLDEN_EGGS, CRYSTAL_EGGS, PHOENIX_EGGS, LEGENDARY_EGGS
}

@Serializable
@Entity(tableName = "resources")
data class ResourceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerId: String,
    val resourceType: String, // ResourceType enum name
    val quantity: Long = 0,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Serializable
data class Resource(
    val type: ResourceType,
    val name: String,
    val description: String,
    val icon: String,
    val quantity: Long
)

@Serializable
@Entity(tableName = "market_listings")
data class MarketListingEntity(
    @PrimaryKey
    val listingId: String,
    val playerId: String,
    val sellerName: String,
    val itemType: String,
    val itemId: String,
    val quantity: Int,
    val pricePerItem: Long,
    val totalPrice: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000),
    @ColumnInfo(name = "is_sold")
    val isSold: Boolean = false,
    val soldAt: Long? = null
)

@Serializable
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val transactionId: String,
    val playerId: String,
    val type: String, // BUY, SELL, TRADE, REWARD, UPGRADE
    val amount: Long,
    val currency: String, // CurrencyType enum name
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val relatedItemId: String? = null
)

@Serializable
@Entity(tableName = "shop_items")
data class ShopItemEntity(
    @PrimaryKey
    val itemId: String,
    val name: String,
    val description: String,
    val icon: String,
    val priceCoins: Long = 0,
    val priceGems: Long = 0,
    val quantity: Int = 1,
    val category: String = "GENERAL",
    @ColumnInfo(name = "is_limited")
    val isLimited: Boolean = false,
    val availableUntil: Long? = null
)

@Serializable
data class MarketStats(
    val totalListings: Int,
    val averagePrice: Long,
    val mostTradedItem: String,
    val totalTransactions: Long,
    val totalVolume: Long
)

@Serializable
data class PlayerEconomy(
    val coins: Long,
    val gems: Long,
    val skyCrystals: Long,
    val totalSpent: Long,
    val totalEarned: Long,
    val netWorth: Long
)
