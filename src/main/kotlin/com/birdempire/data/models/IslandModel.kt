package com.birdempire.data.models

import androidx.room.*
import kotlinx.serialization.Serializable

enum class IslandType {
    TROPICAL, FOREST, SNOW, VOLCANO, CRYSTAL, FANTASY
}

enum class WeatherType {
    SUNNY, RAINY, STORMY, SNOWY, FOGGY, CLEAR
}

enum class TerrainType {
    GRASS, SAND, SNOW, LAVA, CRYSTAL, MYSTICAL
}

@Serializable
@Entity(tableName = "islands")
data class IslandEntity(
    @PrimaryKey
    val islandId: String,
    val playerId: String,
    val name: String,
    val level: Int = 1,
    val type: String = "TROPICAL", // IslandType enum name
    val terrain: String = "GRASS",
    val weather: String = "SUNNY",
    val xp: Long = 0,
    val totalCoins: Long = 0,
    val totalEggsProduced: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,
    val cameraZoom: Float = 1f,
    val decorationCount: Int = 0,
    val buildingCount: Int = 0
)

@Serializable
@Entity(tableName = "buildings")
data class BuildingEntity(
    @PrimaryKey
    val buildingId: String,
    val islandId: String,
    val playerId: String,
    val buildingType: String,
    val level: Int = 1,
    val xp: Long = 0,
    val positionX: Float = 0f,
    val positionY: Float = 0f,
    val productionBonus: Float = 1f,
    val upgradeCost: Long = 100,
    val isConstructing: Boolean = false,
    val constructionEndTime: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Serializable
enum class BuildingType {
    BIRD_CASTLE,
    BIRD_HOUSE,
    FOOD_FARM,
    EGG_FACTORY,
    MARKET,
    RESEARCH_CENTER,
    TRAINING_AREA,
    MAGIC_TOWER
}

@Serializable
data class IslandStats(
    val totalIslands: Int,
    val activeIslands: Int,
    val averageLevel: Float,
    val totalProduction: Long,
    val totalBuildings: Int
)

@Serializable
data class IslandLayout(
    val islandId: String,
    val buildings: List<BuildingEntity>,
    val decorations: List<DecorationEntity>,
    val birds: List<BirdEntity>
)

@Serializable
@Entity(tableName = "decorations")
data class DecorationEntity(
    @PrimaryKey
    val decorationId: String,
    val islandId: String,
    val playerId: String,
    val decorationType: String,
    val positionX: Float,
    val positionY: Float,
    val rotation: Float = 0f,
    val scale: Float = 1f,
    val createdAt: Long = System.currentTimeMillis()
)
