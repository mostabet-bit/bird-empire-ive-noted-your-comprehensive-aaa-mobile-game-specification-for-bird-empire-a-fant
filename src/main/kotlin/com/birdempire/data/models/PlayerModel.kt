package com.birdempire.data.models

import androidx.room.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    val playerId: String,
    val username: String,
    val email: String,
    val passwordHash: String,
    val level: Int = 1,
    val totalXP: Long = 0,
    val coins: Long = 1000,
    val gems: Long = 50,
    val skyCrystals: Long = 10,
    val islandName: String = "New Island",
    val avatarUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis(),
    val totalPlayTime: Long = 0,
    @ColumnInfo(name = "is_premium")
    val isPremium: Boolean = false,
    val cloudSaveId: String = "",
    val isOnline: Boolean = false
)

@Serializable
data class PlayerProfile(
    val playerId: String,
    val username: String,
    val level: Int,
    val totalXP: Long,
    val coins: Long,
    val gems: Long,
    val skyCrystals: Long,
    val islandName: String,
    val avatarUrl: String,
    val totalBirds: Int,
    val totalBuildings: Int,
    val achievements: List<String>,
    val joinDate: Long,
    val isPremium: Boolean
)

@Serializable
@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerId: String,
    val achievementId: String,
    val name: String,
    val description: String,
    val icon: String,
    val unlockedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_unlocked")
    val isUnlocked: Boolean = true
)

@Serializable
data class Achievement(
    val achievementId: String,
    val name: String,
    val description: String,
    val icon: String,
    val reward: Long,
    val requirement: Int
)
