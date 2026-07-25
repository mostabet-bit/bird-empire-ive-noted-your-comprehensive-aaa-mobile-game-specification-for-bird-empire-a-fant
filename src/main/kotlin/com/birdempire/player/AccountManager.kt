package com.birdempire.player

import com.birdempire.data.models.PlayerEntity
import com.birdempire.data.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountManager @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    private var currentPlayer: PlayerEntity? = null

    suspend fun createAccount(
        username: String,
        email: String,
        passwordHash: String
    ): PlayerEntity {
        val player = PlayerEntity(
            playerId = generatePlayerId(),
            username = username,
            email = email,
            passwordHash = passwordHash
        )
        playerRepository.createPlayer(player)
        currentPlayer = player
        return player
    }

    suspend fun login(email: String, passwordHash: String): PlayerEntity? {
        val player = playerRepository.getPlayerByEmail(email)
        return if (player?.passwordHash == passwordHash) {
            currentPlayer = player
            playerRepository.updateLastLogin(player.playerId)
            player
        } else {
            null
        }
    }

    fun getCurrentPlayer(): PlayerEntity? = currentPlayer

    suspend fun updateProfile(player: PlayerEntity) {
        playerRepository.updatePlayer(player)
        currentPlayer = player
    }

    suspend fun addXP(playerId: String, xp: Long) {
        val player = playerRepository.getPlayer(playerId) ?: return
        val newTotalXP = player.totalXP + xp
        playerRepository.updatePlayer(player.copy(totalXP = newTotalXP))
    }

    suspend fun addCurrency(
        playerId: String,
        coinAmount: Long = 0,
        gemAmount: Long = 0,
        crystalAmount: Long = 0
    ) {
        val player = playerRepository.getPlayer(playerId) ?: return
        playerRepository.updatePlayer(
            player.copy(
                coins = player.coins + coinAmount,
                gems = player.gems + gemAmount,
                skyCrystals = player.skyCrystals + crystalAmount
            )
        )
    }

    suspend fun removeCurrency(
        playerId: String,
        coinAmount: Long = 0,
        gemAmount: Long = 0,
        crystalAmount: Long = 0
    ): Boolean {
        val player = playerRepository.getPlayer(playerId) ?: return false
        
        if (player.coins < coinAmount || 
            player.gems < gemAmount || 
            player.skyCrystals < crystalAmount) {
            return false
        }

        playerRepository.updatePlayer(
            player.copy(
                coins = player.coins - coinAmount,
                gems = player.gems - gemAmount,
                skyCrystals = player.skyCrystals - crystalAmount
            )
        )
        return true
    }

    suspend fun unlockAchievement(playerId: String, achievementId: String) {
        playerRepository.unlockAchievement(playerId, achievementId)
    }

    suspend fun getPlayerStats(playerId: String): PlayerStats? {
        val player = playerRepository.getPlayer(playerId) ?: return null
        val birds = playerRepository.getPlayerBirds(playerId)
        val buildings = playerRepository.getPlayerBuildings(playerId)
        val islands = playerRepository.getPlayerIslands(playerId)

        return PlayerStats(
            playerId = playerId,
            level = player.level,
            totalXP = player.totalXP,
            coins = player.coins,
            gems = player.gems,
            skyCrystals = player.skyCrystals,
            totalBirds = birds.size,
            totalBuildings = buildings.size,
            totalIslands = islands.size,
            joinDate = player.createdAt,
            totalPlayTime = player.totalPlayTime
        )
    }

    private fun generatePlayerId(): String = "player_${System.currentTimeMillis()}_${(0..9999).random()}"
}

data class PlayerStats(
    val playerId: String,
    val level: Int,
    val totalXP: Long,
    val coins: Long,
    val gems: Long,
    val skyCrystals: Long,
    val totalBirds: Int,
    val totalBuildings: Int,
    val totalIslands: Int,
    val joinDate: Long,
    val totalPlayTime: Long
)
