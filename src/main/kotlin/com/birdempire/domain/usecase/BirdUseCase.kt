package com.birdempire.domain.usecase

import com.birdempire.data.models.BirdEntity
import com.birdempire.data.repository.BirdRepository
import com.birdempire.data.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.*

@Singleton
class BirdUseCase @Inject constructor(
    private val birdRepository: BirdRepository,
    private val playerRepository: PlayerRepository
) {
    suspend fun createBird(playerId: String, birdType: String, name: String): Result<BirdEntity> {
        return try {
            val bird = BirdEntity(
                birdId = UUID.randomUUID().toString(),
                playerId = playerId,
                birdType = birdType,
                name = name,
                level = 1,
                xp = 0,
                health = 100f,
                hunger = 100f,
                happiness = 100f,
                productionSpeed = 1f,
                price = 100,
                rarity = "COMMON"
            )
            birdRepository.addBird(bird)
            Result.success(bird)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getPlayerBirds(playerId: String): Flow<List<BirdEntity>> {
        return birdRepository.getPlayerBirds(playerId)
    }

    suspend fun feedBird(birdId: String): Result<Unit> {
        return try {
            birdRepository.feedBird(birdId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun upgradeBird(playerId: String, birdId: String, costInCoins: Long): Result<Unit> {
        return try {
            // Deduct coins
            val player = playerRepository.getPlayer(playerId) 
                ?: return Result.failure(Exception("Player not found"))
            
            if (player.coins < costInCoins) {
                return Result.failure(Exception("Insufficient coins"))
            }

            playerRepository.updatePlayer(player.copy(coins = player.coins - costInCoins))
            birdRepository.upgradeBird(birdId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun produceEgg(birdId: String): Result<Boolean> {
        return try {
            val result = birdRepository.produceEgg(birdId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun breedBirds(playerId: String, bird1Id: String, bird2Id: String): Result<Unit> {
        return try {
            birdRepository.breedBirds(bird1Id, bird2Id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sellBird(playerId: String, birdId: String, price: Long): Result<Unit> {
        return try {
            val bird = birdRepository.getBird(birdId) 
                ?: return Result.failure(Exception("Bird not found"))
            
            val player = playerRepository.getPlayer(playerId) 
                ?: return Result.failure(Exception("Player not found"))

            playerRepository.updatePlayer(player.copy(coins = player.coins + price))
            birdRepository.removeBird(bird)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
