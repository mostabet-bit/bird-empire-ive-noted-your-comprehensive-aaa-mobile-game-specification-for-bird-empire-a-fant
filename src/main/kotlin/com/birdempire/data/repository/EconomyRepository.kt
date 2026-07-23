package com.birdempire.data.repository

import com.birdempire.data.database.*
import com.birdempire.data.models.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EconomyRepository @Inject constructor(
    private val resourceDao: ResourceDao,
    private val marketDao: MarketDao,
    private val transactionDao: TransactionDao
) {
    suspend fun addResource(resource: ResourceEntity) {
        resourceDao.insertResource(resource)
    }

    suspend fun getResource(playerId: String, resourceType: String): ResourceEntity? {
        return resourceDao.getResource(playerId, resourceType)
    }

    fun getPlayerResources(playerId: String): Flow<List<ResourceEntity>> {
        return resourceDao.getPlayerResources(playerId)
    }

    suspend fun updateResource(resource: ResourceEntity) {
        resourceDao.updateResource(resource)
    }

    suspend fun addCoins(playerId: String, amount: Long) {
        val resource = resourceDao.getResource(playerId, "COINS") 
            ?: ResourceEntity(playerId = playerId, resourceType = "COINS", quantity = 0)
        resourceDao.insertResource(resource.copy(quantity = resource.quantity + amount))
    }

    suspend fun spendCoins(playerId: String, amount: Long): Boolean {
        val resource = resourceDao.getResource(playerId, "COINS") ?: return false
        if (resource.quantity < amount) return false
        resourceDao.updateResource(resource.copy(quantity = resource.quantity - amount))
        return true
    }

    suspend fun createMarketListing(listing: MarketListingEntity) {
        marketDao.insertListing(listing)
    }

    fun getActiveListings(): Flow<List<MarketListingEntity>> {
        return marketDao.getActiveListings()
    }

    fun getListingsByType(itemType: String): Flow<List<MarketListingEntity>> {
        return marketDao.getListingsByType(itemType)
    }

    suspend fun purchaseListing(listingId: String): Boolean {
        // Implement purchase logic
        return true
    }

    suspend fun recordTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
    }

    fun getPlayerTransactions(playerId: String): Flow<List<TransactionEntity>> {
        return transactionDao.getPlayerTransactions(playerId)
    }

    suspend fun getTotalEarnings(playerId: String): Long {
        return transactionDao.getTotalEarnings(playerId) ?: 0L
    }

    suspend fun getTotalSpent(playerId: String): Long {
        return transactionDao.getTotalSpent(playerId) ?: 0L
    }

    suspend fun addShopItem(item: ShopItemEntity) {
        marketDao.insertShopItem(item)
    }

    fun getShopItems(category: String): Flow<List<ShopItemEntity>> {
        return marketDao.getShopItems(category)
    }
}
