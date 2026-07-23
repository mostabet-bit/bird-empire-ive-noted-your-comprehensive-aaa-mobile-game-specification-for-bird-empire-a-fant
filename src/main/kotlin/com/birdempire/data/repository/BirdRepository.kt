package com.birdempire.data.repository

import com.birdempire.data.database.BirdDao
import com.birdempire.data.models.BirdEntity
import com.birdempire.data.models.BirdInventoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BirdRepository @Inject constructor(
    private val birdDao: BirdDao
) {
    suspend fun addBird(bird: BirdEntity) {
        birdDao.insertBird(bird)
    }

    suspend fun getBird(birdId: String): BirdEntity? {
        return birdDao.getBird(birdId)
    }

    fun getPlayerBirds(playerId: String): Flow<List<BirdEntity>> {
        return birdDao.getPlayerBirds(playerId)
    }

    fun getActiveBirds(playerId: String): Flow<List<BirdEntity>> {
        return birdDao.getActiveBirds(playerId)
    }

    suspend fun updateBird(bird: BirdEntity) {
        birdDao.updateBird(bird)
    }

    suspend fun removeBird(bird: BirdEntity) {
        birdDao.deleteBird(bird)
    }

    suspend fun getBirdCount(playerId: String): Int {
        return birdDao.getBirdCount(playerId)
    }

    suspend fun getBirdCountByRarity(playerId: String, rarity: String): Int {
        return birdDao.getBirdCountByRarity(playerId, rarity)
    }

    suspend fun feedBird(birdId: String) {
        val bird = birdDao.getBird(birdId) ?: return
        birdDao.updateBird(
            bird.copy(
                hunger = minOf(100f, bird.hunger + 30f),
                lastFedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun upgradeBird(birdId: String) {
        val bird = birdDao.getBird(birdId) ?: return
        birdDao.updateBird(
            bird.copy(
                level = bird.level + 1,
                productionSpeed = bird.productionSpeed * 1.1f
            )
        )
    }

    suspend fun addBirdToInventory(inventory: BirdInventoryEntity) {
        birdDao.insertBirdInventory(inventory)
    }

    fun getBirdInventory(playerId: String): Flow<List<BirdInventoryEntity>> {
        return birdDao.getBirdInventory(playerId)
    }

    suspend fun produceEgg(birdId: String): Boolean {
        val bird = birdDao.getBird(birdId) ?: return false
        
        val timeSinceProduction = (System.currentTimeMillis() - bird.lastProducedAt) / 1000
        if (timeSinceProduction < 30) return false

        birdDao.updateBird(
            bird.copy(
                lastProducedAt = System.currentTimeMillis(),
                xp = bird.xp + 10
            )
        )
        return true
    }

    suspend fun breedBirds(bird1Id: String, bird2Id: String): BirdEntity? {
        val bird1 = birdDao.getBird(bird1Id) ?: return null
        val bird2 = birdDao.getBird(bird2Id) ?: return null

        birdDao.updateBird(bird1.copy(isBreeding = true, breedingPartner = bird2Id))
        birdDao.updateBird(bird2.copy(isBreeding = true, breedingPartner = bird1Id))

        return null
    }
}
