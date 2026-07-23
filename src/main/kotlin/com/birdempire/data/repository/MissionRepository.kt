package com.birdempire.data.repository

import com.birdempire.data.database.MissionDao
import com.birdempire.data.models.MissionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MissionRepository @Inject constructor(
    private val missionDao: MissionDao
) {
    suspend fun createMission(mission: MissionEntity) {
        missionDao.insertMission(mission)
    }

    fun getDailyMissions(playerId: String): Flow<List<MissionEntity>> {
        return missionDao.getDailyMissions(playerId)
    }

    fun getAvailableMissions(playerId: String): Flow<List<MissionEntity>> {
        return missionDao.getAvailableMissions(playerId)
    }

    suspend fun updateMission(mission: MissionEntity) {
        missionDao.updateMission(mission)
    }

    suspend fun progressMission(missionId: String, progress: Int) {
        // Fetch and update mission progress
    }

    suspend fun completeMission(missionId: String) {
        // Mark mission as completed
    }

    suspend fun claimMissionReward(missionId: String) {
        // Claim mission reward
    }

    suspend fun getUnclaimedMissions(playerId: String): List<MissionEntity> {
        return missionDao.getUnclaimedMissions(playerId)
    }
}
