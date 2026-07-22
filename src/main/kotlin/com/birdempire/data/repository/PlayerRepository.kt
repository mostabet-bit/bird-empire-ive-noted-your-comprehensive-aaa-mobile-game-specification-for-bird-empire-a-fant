package com.birdempire.data.repository

import com.birdempire.data.database.PlayerDao
import com.birdempire.data.models.PlayerEntity
import com.birdempire.data.models.AchievementEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val playerDao: PlayerDao
) {
    suspend fun createPlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }

    suspend fun getPlayer(playerId: String): PlayerEntity? {
        return playerDao.getPlayer(playerId)
    }

    suspend fun getPlayerByUsername(username: String): PlayerEntity? {
        return playerDao.getPlayerByUsername(username)
    }

    suspend fun updatePlayer(player: PlayerEntity) {
        playerDao.updatePlayer(player)
    }

    fun getTopPlayers(): Flow<List<PlayerEntity>> {
        return playerDao.getTopPlayers()
    }

    suspend fun addCoins(playerId: String, amount: Long) {
        val player = playerDao.getPlayer(playerId) ?: return
        playerDao.updatePlayer(player.copy(coins = player.coins + amount))
    }

    suspend fun addGems(playerId: String, amount: Long) {
        val player = playerDao.getPlayer(playerId) ?: return
        playerDao.updatePlayer(player.copy(gems = player.gems + amount))
    }

    suspend fun addSkyCrystals(playerId: String, amount: Long) {
        val player = playerDao.getPlayer(playerId) ?: return
        playerDao.updatePlayer(player.copy(skyCrystals = player.skyCrystals + amount))
    }

    suspend fun addXP(playerId: String, xp: Long) {
        val player = playerDao.getPlayer(playerId) ?: return
        val newXP = player.totalXP + xp
        playerDao.updatePlayer(player.copy(totalXP = newXP))
    }

    suspend fun unlockAchievement(achievement: AchievementEntity) {
        playerDao.insertAchievement(achievement)
    }

    fun getAchievements(playerId: String): Flow<List<AchievementEntity>> {
        return playerDao.getAchievements(playerId)
    }

    suspend fun updateLastLogin(playerId: String) {
        val player = playerDao.getPlayer(playerId) ?: return
        playerDao.updatePlayer(player.copy(lastLoginAt = System.currentTimeMillis()))
    }

    suspend fun setOnlineStatus(playerId: String, isOnline: Boolean) {
        val player = playerDao.getPlayer(playerId) ?: return
        playerDao.updatePlayer(player.copy(isOnline = isOnline))
    }
}
