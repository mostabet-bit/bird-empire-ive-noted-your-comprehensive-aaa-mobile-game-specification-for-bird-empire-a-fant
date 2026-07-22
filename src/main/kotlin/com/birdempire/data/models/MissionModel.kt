package com.birdempire.data.models

import androidx.room.*
import kotlinx.serialization.Serializable

enum class MissionType {
    DAILY, EVENT, SEASONAL, SPECIAL
}

enum class MissionStatus {
    AVAILABLE, IN_PROGRESS, COMPLETED, CLAIMED, EXPIRED
}

@Serializable
@Entity(tableName = "missions")
data class MissionEntity(
    @PrimaryKey
    val missionId: String,
    val playerId: String,
    val title: String,
    val description: String,
    val type: String = "DAILY", // MissionType enum name
    val status: String = "AVAILABLE", // MissionStatus enum name
    val objective: String,
    val currentProgress: Int = 0,
    val targetProgress: Int = 1,
    val rewardXP: Long = 100,
    val rewardCoins: Long = 500,
    val rewardGems: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (24 * 60 * 60 * 1000),
    val completedAt: Long? = null,
    val claimedAt: Long? = null
)

@Serializable
@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val eventId: String,
    val name: String,
    val description: String,
    val type: String, // SEASONAL, SPECIAL, TOURNAMENT
    val startTime: Long,
    val endTime: Long,
    val icon: String,
    val rewards: List<String>,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true
)

@Serializable
@Entity(tableName = "leaderboard")
data class LeaderboardEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerId: String,
    val playerName: String,
    val playerLevel: Int,
    val score: Long,
    val rank: Int,
    val totalBirds: Int,
    val totalIslands: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Serializable
data class Mission(
    val missionId: String,
    val title: String,
    val description: String,
    val type: MissionType,
    val status: MissionStatus,
    val objective: String,
    val progress: Int,
    val target: Int,
    val rewards: MissionReward
)

@Serializable
data class MissionReward(
    val xp: Long,
    val coins: Long,
    val gems: Long,
    val items: List<String> = emptyList()
)
