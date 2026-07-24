package com.birdempire.economy

import com.birdempire.core.GameConstants
import com.birdempire.data.models.*
import javax.inject.Inject
import javax.inject.Singleton

enum class CurrencyType {
    COINS, GEMS, SKY_CRYSTALS
}

@Singleton
class EconomyManager @Inject constructor() {

    fun addCurrency(
        playerId: String,
        type: CurrencyType,
        amount: Long
    ): Long {
        return amount // In real app, update database
    }

    fun removeCurrency(
        playerId: String,
        type: CurrencyType,
        amount: Long
    ): Boolean {
        return amount >= 0 // In real app, check balance
    }

    fun canAfford(
        playerId: String,
        type: CurrencyType,
        amount: Long
    ): Boolean {
        return true // In real app, check player balance
    }

    fun createMarketListing(
        playerId: String,
        itemName: String,
        quantity: Int,
        price: Long,
        itemType: String
    ): MarketListingEntity {
        return MarketListingEntity(
            listingId = generateListingId(),
            playerId = playerId,
            sellerName = "Player", // Get from player data
            itemName = itemName,
            itemType = itemType,
            quantity = quantity,
            price = price,
            createdAt = System.currentTimeMillis(),
            expiresAt = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000) // 7 days
        )
    }

    fun purchaseListing(
        buyerId: String,
        listing: MarketListingEntity
    ): TransactionEntity {
        return TransactionEntity(
            transactionId = generateTransactionId(),
            buyerId = buyerId,
            sellerId = listing.playerId,
            itemName = listing.itemName,
            quantity = listing.quantity,
            price = listing.price,
            totalPrice = listing.price * listing.quantity,
            timestamp = System.currentTimeMillis(),
            status = "COMPLETED"
        )
    }

    fun calculateTax(amount: Long): Long {
        return (amount * GameConstants.MARKET_TAX_PERCENTAGE) / 100
    }

    fun createShopItem(
        itemId: String,
        name: String,
        description: String,
        price: Long,
        currencyType: CurrencyType,
        quantity: Int = 1
    ): ShopItemEntity {
        return ShopItemEntity(
            itemId = itemId,
            name = name,
            description = description,
            price = price,
            currencyType = currencyType.name,
            quantity = quantity,
            isAvailable = true,
            createdAt = System.currentTimeMillis()
        )
    }

    fun purchaseShopItem(
        playerId: String,
        item: ShopItemEntity
    ): Boolean {
        // Check if player can afford
        // Deduct currency
        // Add item to inventory
        return true
    }

    private fun generateListingId(): String = "listing_${System.currentTimeMillis()}_${(0..9999).random()}"
    private fun generateTransactionId(): String = "txn_${System.currentTimeMillis()}_${(0..9999).random()}"
}
