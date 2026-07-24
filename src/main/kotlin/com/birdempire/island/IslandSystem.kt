package com.birdempire.island

import com.birdempire.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class IslandType {
    TROPICAL, FOREST, SNOW, VOLCANO, CRYSTAL, FANTASY
}

enum class TerrainType {
    GRASS, SAND, SNOW, LAVA, CRYSTAL, WATER
}

enum class WeatherType {
    SUNNY, CLOUDY, RAINY, SNOWY, STORMY, CLEAR
}

@Singleton
class IslandSystem @Inject constructor() {
    private val islands = MutableStateFlow<List<IslandEntity>>(emptyList())

    fun createIsland(playerId: String, name: String, type: IslandType): IslandEntity {
        return IslandEntity(
            islandId = generateIslandId(),
            playerId = playerId,
            name = name,
            level = 1,
            type = type.name,
            terrain = TerrainType.GRASS.name,
            weather = WeatherType.SUNNY.name,
            resources = 1000,
            maxCapacity = 50,
            createdAt = System.currentTimeMillis()
        )
    }

    fun upgradeIsland(island: IslandEntity): IslandEntity {
        val newLevel = island.level + 1
        return island.copy(
            level = newLevel,
            maxCapacity = island.maxCapacity + 10,
            resources = island.resources + (newLevel * 100)
        )
    }

    fun addBuilding(island: IslandEntity, building: BuildingEntity): IslandEntity {
        return island.copy(
            buildingCount = island.buildingCount + 1,
            resources = island.resources - building.upgradeCost
        )
    }

    fun removeBuilding(island: IslandEntity): IslandEntity {
        return island.copy(
            buildingCount = maxOf(island.buildingCount - 1, 0)
        )
    }

    fun updateWeather(island: IslandEntity, weather: WeatherType): IslandEntity {
        return island.copy(weather = weather.name)
    }

    fun updateTerrain(island: IslandEntity, terrain: TerrainType): IslandEntity {
        return island.copy(terrain = terrain.name)
    }

    fun addDecoration(island: IslandEntity, decoration: DecorationEntity): IslandEntity {
        return island.copy(
            decorationCount = island.decorationCount + 1,
            resources = island.resources - decoration.cost
        )
    }

    private fun generateIslandId(): String = "island_${System.currentTimeMillis()}_${(0..9999).random()}"
}
