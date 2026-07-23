package com.birdempire.domain.usecase

import com.birdempire.data.models.MissionEntity
import com.birdempire.data.repository.MissionRepository
import com.birdempire.data.repository.PlayerRepository
import com.birdempire.core.GameConstants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.*

@Singleton
class MissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository,
    private val playerRepository: PlayerRepository
) {
    suspend fun createDailyMissions(playerId: String): Result<List<MissionEntity>> {
        return try {
            val missions = listOf(
                MissionEntity(
                    missionId = UUID.randomUUID().toString(),
                    playerId = playerId,
                    missionType = "FEED_BIRDS",
                    title = "Feed Your Birds",
                    description = "Feed 5 birds",
                    progress = 0,
                    target = 5,
                    reward = GameConstants.MISSION_REWARD_COINS,
                    rewardXP = GameConstants.MISSION_REWARD_XP,
                    isCompleted = false,
                    isClaimed = false,
                    createdAt = System.currentTimeMillis()
                ),
                MissionEntity(
                    missionId = UUID.randomUUID().toString(),
                    playerId = playerId,
                    missionType = "COLLECT_EGGS",
                    title = "Collect Eggs",
                    description = "Collect 10 eggs",
                    progress = 0,
                    target = 10,
                    reward = GameConstants.MISSION_REWARD_COINS,
                    rewardXP = GameConstants.MISSION_REWARD_XP,
                    isCompleted = false,
                    isClaimed = false,
                    createdAt = System.currentTimeMillis()
                ),
                MissionEntity(
                    missionId = UUID.randomUUID().toString(),
                    playerId = playerId,
                    missionType = "UPGRADE_BUILDING",
                    title = "Upgrade Building",
                    description = "Upgrade 1 building",
                    progress = 0,
                    target = 1,
                    reward = GameConstants.MISSION_REWARD_COINS,
                    rewardXP = GameConstants.MISSION_REWARD_XP,
                    isCompleted = false,
                    isClaimed = false,
                    createdAt = System.currentTimeMillis()
                )
            )

            missions.forEach { missionRepository.createMission(it) }
            Result.success(missions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getDailyMissions(playerId: String): Flow<List<MissionEntity>> {
        return missionRepository.getDailyMissions(playerId)
    }

    suspend fun progressMission(playerId: String, missionId: String): Result<Unit> {
        return try {
            // Implement mission progress logic
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun claimMissionReward(playerId: String, missionId: String): Result<Unit> {
        return try {
            val player = playerRepository.getPlayer(playerId) 
                ?: return Result.failure(Exception("Player not found"))

            val mission = missionRepository.getDailyMissions(playerId).collect { missions ->
                missions.find { it.missionId == missionId }
            }

            // Add reward to player
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
