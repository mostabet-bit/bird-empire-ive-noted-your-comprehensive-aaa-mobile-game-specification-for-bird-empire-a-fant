package com.birdempire.data.database

import androidx.room.*
import com.birdempire.data.models.*

@Database(
    entities = [
        PlayerEntity::class,
        AchievementEntity::class,
        BirdEntity::class,
        BirdInventoryEntity::class,
        IslandEntity::class,
        BuildingEntity::class,
        DecorationEntity::class,
        ResourceEntity::class,
        MarketListingEntity::class,
        TransactionEntity::class,
        ShopItemEntity::class,
        MissionEntity::class,
        EventEntity::class,
        LeaderboardEntry::class
    ],
    version = 1,
    exportSchema = true
)
abstract class BirdEmpireDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun birdDao(): BirdDao
    abstract fun islandDao(): IslandDao
    abstract fun buildingDao(): BuildingDao
    abstract fun decorationDao(): DecorationDao
    abstract fun resourceDao(): ResourceDao
    abstract fun marketDao(): MarketDao
    abstract fun transactionDao(): TransactionDao
    abstract fun missionDao(): MissionDao
    abstract fun leaderboardDao(): LeaderboardDao
}
