package com.birdempire.domain.usecase

import com.birdempire.data.models.IslandEntity
import com.birdempire.data.models.BuildingEntity
import com.birdempire.data.repository.IslandRepository
import com.birdempire.data.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.*

@Singleton
class IslandUseCase @Inject constructor(
    private val islandRepository: IslandRepository,
    private val playerRepository: PlayerRepository
) {
    suspend fun createIsland(playerId: String, islandName: String, terrain: String): Result<IslandEntity> {
        return try {
            val island = IslandEntity(
                islandId = UUID.randomUUID().toString(),
                playerId = playerId,
                name = islandName,
                level = 1,
                terrain = terrain,
                weather = "SUNNY",
                createdAt = System.currentTimeMillis()
            )
            islandRepository.createIsland(island)
            Result.success(island)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getPlayerIslands(playerId: String): Flow<List<IslandEntity>> {
        return islandRepository.getPlayerIslands(playerId)
    }

    suspend fun addBuilding(playerId: String, islandId: String, buildingType: String, costInCoins: Long): Result<BuildingEntity> {
        return try {
            val player = playerRepository.getPlayer(playerId) 
                ?: return Result.failure(Exception("Player not found"))
            
            if (player.coins < costInCoins) {
                return Result.failure(Exception("Insufficient coins"))
            }

            val building = BuildingEntity(
                buildingId = UUID.randomUUID().toString(),
                islandId = islandId,
                buildingType = buildingType,
                level = 1,
                upgradeCost = (costInCoins * 1.5).toLong(),
                productionBonus = 1.0f,
                createdAt = System.currentTimeMillis()
            )

            playerRepository.updatePlayer(player.copy(coins = player.coins - costInCoins))
            islandRepository.addBuilding(building)
            Result.success(building)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun upgradeBuilding(playerId: String, buildingId: String): Result<Unit> {
        return try {
            val building = islandRepository.getBuilding(buildingId) 
                ?: return Result.failure(Exception("Building not found"))
            
            val player = playerRepository.getPlayer(playerId) 
                ?: return Result.failure(Exception("Player not found"))

            if (player.coins < building.upgradeCost) {
                return Result.failure(Exception("Insufficient coins"))
            }

            playerRepository.updatePlayer(player.copy(coins = player.coins - building.upgradeCost))
            islandRepository.upgradeBuild(buildingId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getIslandBuildings(islandId: String): Flow<List<BuildingEntity>> {
        return islandRepository.getIslandBuildings(islandId)
    }

    suspend fun updateWeather(islandId: String, weather: String): Result<Unit> {
        return try {
            islandRepository.updateWeather(islandId, weather)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
