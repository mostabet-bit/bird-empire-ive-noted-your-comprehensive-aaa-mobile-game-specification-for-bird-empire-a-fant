package com.birdempire.economy

import com.birdempire.data.models.ShopItemEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopManager @Inject constructor(
    private val economyManager: EconomyManager
) {
    private val shopItems = mutableListOf<ShopItemEntity>()

    init {
        initializeShop()
    }

    private fun initializeShop() {
        // Gem packs
        shopItems.add(
            economyManager.createShopItem(
                itemId = "gems_50",
                name = "50 Gems",
                description = "Small gem pack",
                price = 4_99,
                currencyType = CurrencyType.GEMS,
                quantity = 50
            )
        )

        shopItems.add(
            economyManager.createShopItem(
                itemId = "gems_500",
                name = "500 Gems",
                description = "Large gem pack",
                price = 29_99,
                currencyType = CurrencyType.GEMS,
                quantity = 500
            )
        )

        // Starter packs
        shopItems.add(
            economyManager.createShopItem(
                itemId = "starter_pack",
                name = "Starter Pack",
                description = "Perfect for new players",
                price = 9_99,
                currencyType = CurrencyType.GEMS,
                quantity = 1
            )
        )
    }

    fun getShopItems(): List<ShopItemEntity> = shopItems

    fun getItemsByType(type: String): List<ShopItemEntity> {
        return shopItems.filter { it.currencyType == type }
    }

    fun purchaseItem(playerId: String, itemId: String): Boolean {
        val item = shopItems.find { it.itemId == itemId } ?: return false
        return economyManager.purchaseShopItem(playerId, item)
    }
}
