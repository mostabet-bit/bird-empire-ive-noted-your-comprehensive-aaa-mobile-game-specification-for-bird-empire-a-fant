package com.birdempire.data.database

import androidx.room.*
import com.birdempire.data.models.*
import kotlinx.coroutines.flow.Flow

// Player DAO
@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE playerId = :playerId")
    suspend fun getPlayer(playerId: String): PlayerEntity?

    @Query("SELECT * FROM players WHERE username = :username")
    suspend fun getPlayerByUsername(username: String): PlayerEntity?

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players ORDER BY level DESC LIMIT 10")
    fun getTopPlayers(): Flow<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: AchievementEntity)

    @Query("SELECT * FROM achievements WHERE playerId = :playerId")
    fun getAchievements(playerId: String): Flow<List<AchievementEntity>>
}

// Bird DAO
@Dao
interface BirdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBird(bird: BirdEntity)

    @Query("SELECT * FROM birds WHERE birdId = :birdId")
    suspend fun getBird(birdId: String): BirdEntity?

    @Query("SELECT * FROM birds WHERE playerId = :playerId")
    fun getPlayerBirds(playerId: String): Flow<List<BirdEntity>>

    @Query("SELECT * FROM birds WHERE playerId = :playerId AND isActive = 1")
    fun getActiveBirds(playerId: String): Flow<List<BirdEntity>>

    @Update
    suspend fun updateBird(bird: BirdEntity)

    @Delete
    suspend fun deleteBird(bird: BirdEntity)

    @Query("SELECT COUNT(*) FROM birds WHERE playerId = :playerId")
    suspend fun getBirdCount(playerId: String): Int

    @Query("SELECT COUNT(*) FROM birds WHERE playerId = :playerId AND rarity = :rarity")
    suspend fun getBirdCountByRarity(playerId: String, rarity: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBirdInventory(inventory: BirdInventoryEntity)

    @Query("SELECT * FROM bird_inventory WHERE playerId = :playerId")
    fun getBirdInventory(playerId: String): Flow<List<BirdInventoryEntity>>
}

// Island DAO
@Dao
interface IslandDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIsland(island: IslandEntity)

    @Query("SELECT * FROM islands WHERE islandId = :islandId")
    suspend fun getIsland(islandId: String): IslandEntity?

    @Query("SELECT * FROM islands WHERE playerId = :playerId")
    fun getPlayerIslands(playerId: String): Flow<List<IslandEntity>>

    @Update
    suspend fun updateIsland(island: IslandEntity)

    @Query("SELECT COUNT(*) FROM islands WHERE playerId = :playerId")
    suspend fun getIslandCount(playerId: String): Int
}

// Building DAO
@Dao
interface BuildingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuilding(building: BuildingEntity)

    @Query("SELECT * FROM buildings WHERE buildingId = :buildingId")
    suspend fun getBuilding(buildingId: String): BuildingEntity?

    @Query("SELECT * FROM buildings WHERE islandId = :islandId")
    fun getIslandBuildings(islandId: String): Flow<List<BuildingEntity>>

    @Update
    suspend fun updateBuilding(building: BuildingEntity)

    @Delete
    suspend fun deleteBuilding(building: BuildingEntity)

    @Query("SELECT COUNT(*) FROM buildings WHERE islandId = :islandId")
    suspend fun getBuildingCount(islandId: String): Int
}

// Decoration DAO
@Dao
interface DecorationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecoration(decoration: DecorationEntity)

    @Query("SELECT * FROM decorations WHERE islandId = :islandId")
    fun getIslandDecorations(islandId: String): Flow<List<DecorationEntity>>

    @Update
    suspend fun updateDecoration(decoration: DecorationEntity)

    @Delete
    suspend fun deleteDecoration(decoration: DecorationEntity)
}

// Resource DAO
@Dao
interface ResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resource: ResourceEntity)

    @Query("SELECT * FROM resources WHERE playerId = :playerId AND resourceType = :resourceType")
    suspend fun getResource(playerId: String, resourceType: String): ResourceEntity?

    @Query("SELECT * FROM resources WHERE playerId = :playerId")
    fun getPlayerResources(playerId: String): Flow<List<ResourceEntity>>

    @Update
    suspend fun updateResource(resource: ResourceEntity)
}

// Market DAO
@Dao
interface MarketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListing(listing: MarketListingEntity)

    @Query("SELECT * FROM market_listings WHERE is_sold = 0 ORDER BY createdAt DESC LIMIT 50")
    fun getActiveListings(): Flow<List<MarketListingEntity>>

    @Query("SELECT * FROM market_listings WHERE itemType = :itemType AND is_sold = 0 ORDER BY pricePerItem ASC")
    fun getListingsByType(itemType: String): Flow<List<MarketListingEntity>>

    @Update
    suspend fun updateListing(listing: MarketListingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(item: ShopItemEntity)

    @Query("SELECT * FROM shop_items WHERE category = :category")
    fun getShopItems(category: String): Flow<List<ShopItemEntity>>
}

// Transaction DAO
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE playerId = :playerId ORDER BY timestamp DESC LIMIT 100")
    fun getPlayerTransactions(playerId: String): Flow<List<TransactionEntity>>

    @Query("SELECT SUM(amount) FROM transactions WHERE playerId = :playerId AND type = 'SELL'")
    suspend fun getTotalEarnings(playerId: String): Long?

    @Query("SELECT SUM(amount) FROM transactions WHERE playerId = :playerId AND type = 'BUY'")
    suspend fun getTotalSpent(playerId: String): Long?
}

// Mission DAO
@Dao
interface MissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: MissionEntity)

    @Query("SELECT * FROM missions WHERE playerId = :playerId AND type = 'DAILY' ORDER BY createdAt DESC")
    fun getDailyMissions(playerId: String): Flow<List<MissionEntity>>

    @Query("SELECT * FROM missions WHERE playerId = :playerId AND status = 'AVAILABLE' ORDER BY createdAt DESC")
    fun getAvailableMissions(playerId: String): Flow<List<MissionEntity>>

    @Update
    suspend fun updateMission(mission: MissionEntity)

    @Query("SELECT * FROM missions WHERE playerId = :playerId AND status = 'COMPLETED' AND claimedAt IS NULL")
    suspend fun getUnclaimedMissions(playerId: String): List<MissionEntity>
}

// Leaderboard DAO
@Dao
interface LeaderboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: LeaderboardEntry)

    @Query("SELECT * FROM leaderboard ORDER BY rank ASC LIMIT 100")
    fun getTopPlayers(): Flow<List<LeaderboardEntry>>

    @Query("SELECT * FROM leaderboard WHERE playerId = :playerId")
    suspend fun getPlayerRank(playerId: String): LeaderboardEntry?

    @Query("SELECT * FROM leaderboard WHERE rank >= :startRank AND rank <= :endRank ORDER BY rank ASC")
    fun getLeaderboardRange(startRank: Int, endRank: Int): Flow<List<LeaderboardEntry>>
}
