package com.birdempire.data.models

import androidx.room.*
import kotlinx.serialization.Serializable

enum class BirdRarity {
    COMMON, RARE, EPIC, LEGENDARY
}

enum class BirdType {
    // Common
    SPARROW, BLUE_BIRD, ROBIN,
    // Rare
    FALCON, OWL, PEACOCK,
    // Epic
    EAGLE, GOLDEN_EAGLE,
    // Legendary
    PHOENIX, CRYSTAL_PHOENIX, DRAGON_BIRD
}

@Serializable
@Entity(tableName = "birds")
data class BirdEntity(
    @PrimaryKey
    val birdId: String,
    val playerId: String,
    val birdType: String, // BirdType enum name
    val name: String,
    val level: Int = 1,
    val xp: Long = 0,
    val health: Float = 100f,
    val hunger: Float = 100f,
    val happiness: Float = 100f,
    val productionSpeed: Float = 1f,
    val price: Long = 100,
    val rarity: String = "COMMON",
    val ability: String = "",
    val isActive: Boolean = true,
    val lastFedAt: Long = System.currentTimeMillis(),
    val lastProducedAt: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_breeding")
    val isBreeding: Boolean = false,
    val breedingPartner: String? = null
)

@Serializable
data class BirdSpecies(
    val type: BirdType,
    val name: String,
    val rarity: BirdRarity,
    val baseHealth: Float,
    val baseProduction: Float,
    val basePrice: Long,
    val ability: String,
    val description: String,
    val icon: String
)

@Serializable
@Entity(tableName = "bird_inventory")
data class BirdInventoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerId: String,
    val birdType: String,
    val quantity: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Serializable
data class BirdStats(
    val totalBirds: Int,
    val commonBirds: Int,
    val rareBirds: Int,
    val epicBirds: Int,
    val legendaryBirds: Int,
    val totalEggsProduced: Long,
    val averageLevel: Float
)
