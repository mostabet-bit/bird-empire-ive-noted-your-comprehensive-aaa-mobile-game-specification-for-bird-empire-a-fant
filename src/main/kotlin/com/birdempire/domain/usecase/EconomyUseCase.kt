package com.birdempire.domain.usecase

import com.birdempire.data.models.*
import com.birdempire.data.repository.EconomyRepository
import com.birdempire.data.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.*

@Singleton
class EconomyUseCase @Inject constructor(
    private val economyRepository: EconomyRepository,
    private val playerRepository: PlayerRepository
) {
    suspend fun purchaseFromShop(playerId: String, itemId: String, quantity: Int, cost: Long): Result<Unit> {
        return try {
            val player = playerRepository.getPlayer(playerId) 
                ?: return Result.failure(Exception("Player not found"))

            val totalCost = cost * quantity
            if (player.coins < totalCost) {
                return Result.failure(Exception("Insufficient coins"))
            }

            playerRepository.updatePlayer(player.copy(coins = player.coins - totalCost))

            val transaction = TransactionEntity(
                transactionId = UUID.randomUUID().toString(),
                playerId = playerId,
                type = "PURCHASE",
                amount = totalCost,
                itemId = itemId,
                timestamp = System.currentTimeMillis()
            )
            economyRepository.recordTransaction(transaction)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createMarketListing(playerId: String, itemId: String, itemType: String, price: Long): Result<Unit> {
        return try {
            val listing = MarketListingEntity(
                listingId = UUID.randomUUID().toString(),
                sellerId = playerId,
                itemId = itemId,
                itemType = itemType,
                price = price,
                createdAt = System.currentTimeMillis(),
                isActive = true
            )
            economyRepository.createMarketListing(listing)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getMarketListings(): Flow<List<MarketListingEntity>> {
        return economyRepository.getActiveListings()
    }

    suspend fun purchaseFromMarket(buyerId: String, listingId: String): Result<Unit> {
        return try {
            // Implement market purchase logic
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getPlayerTransactions(playerId: String): Flow<List<TransactionEntity>> {
        return economyRepository.getPlayerTransactions(playerId)
    }

    suspend fun getTotalEarnings(playerId: String): Long {
        return economyRepository.getTotalEarnings(playerId)
    }

    suspend fun getTotalSpent(playerId: String): Long {
        return economyRepository.getTotalSpent(playerId)
    }
}
