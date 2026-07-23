package com.birdempire.domain.usecase

import com.birdempire.data.models.PlayerEntity
import com.birdempire.data.repository.PlayerRepository
import com.birdempire.core.GameConstants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.*

@Singleton
class AuthUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend fun register(username: String, email: String, password: String): Result<PlayerEntity> {
        return try {
            // Validate inputs
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return Result.failure(Exception("Invalid input"))
            }

            // Check if username exists
            if (playerRepository.getPlayerByUsername(username) != null) {
                return Result.failure(Exception("Username already taken"))
            }

            // Create new player
            val playerId = UUID.randomUUID().toString()
            val player = PlayerEntity(
                playerId = playerId,
                username = username,
                email = email,
                passwordHash = hashPassword(password),
                coins = GameConstants.STARTING_COINS,
                gems = GameConstants.STARTING_GEMS,
                skyCrystals = GameConstants.STARTING_SKY_CRYSTALS,
                level = GameConstants.STARTING_LEVEL,
                totalXP = GameConstants.STARTING_XP.toLong()
            )

            playerRepository.createPlayer(player)
            Result.success(player)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<PlayerEntity> {
        return try {
            val player = playerRepository.getPlayerByUsername(username)
                ?: return Result.failure(Exception("Player not found"))

            if (!verifyPassword(password, player.passwordHash)) {
                return Result.failure(Exception("Invalid password"))
            }

            playerRepository.updateLastLogin(player.playerId)
            playerRepository.setOnlineStatus(player.playerId, true)
            Result.success(player)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(playerId: String) {
        playerRepository.setOnlineStatus(playerId, false)
    }

    private fun hashPassword(password: String): String {
        // In production, use bcrypt or similar
        return password.hashCode().toString()
    }

    private fun verifyPassword(password: String, hash: String): Boolean {
        return password.hashCode().toString() == hash
    }
}
