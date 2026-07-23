package com.birdempire.data.repository

import com.birdempire.data.database.*
import com.birdempire.data.models.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IslandRepository @Inject constructor(
    private val islandDao: IslandDao,
    private val buildingDao: BuildingDao,
    private val decorationDao: DecorationDao
) {
    suspend fun createIsland(island: IslandEntity) {
        islandDao.insertIsland(island)
    }

    suspend fun getIsland(islandId: String): IslandEntity? {
        return islandDao.getIsland(islandId)
    }

    fun getPlayerIslands(playerId: String): Flow<List<IslandEntity>> {
        return islandDao.getPlayerIslands(playerId)
    }

    suspend fun updateIsland(island: IslandEntity) {
        islandDao.updateIsland(island)
    }

    suspend fun getIslandCount(playerId: String): Int {
        return islandDao.getIslandCount(playerId)
    }

    suspend fun addBuilding(building: BuildingEntity) {
        buildingDao.insertBuilding(building)
    }

    suspend fun getBuilding(buildingId: String): BuildingEntity? {
        return buildingDao.getBuilding(buildingId)
    }

    fun getIslandBuildings(islandId: String): Flow<List<BuildingEntity>> {
        return buildingDao.getIslandBuildings(islandId)
    }

    suspend fun updateBuilding(building: BuildingEntity) {
        buildingDao.updateBuilding(building)
    }

    suspend fun removeBuilding(building: BuildingEntity) {
        buildingDao.deleteBuilding(building)
    }

    suspend fun upgradeBuild(buildingId: String) {
        val building = buildingDao.getBuilding(buildingId) ?: return
        buildingDao.updateBuilding(
            building.copy(
                level = building.level + 1,
                productionBonus = building.productionBonus * 1.15f,
                upgradeCost = (building.upgradeCost * 1.2).toLong()
            )
        )
    }

    suspend fun addDecoration(decoration: DecorationEntity) {
        decorationDao.insertDecoration(decoration)
    }

    fun getIslandDecorations(islandId: String): Flow<List<DecorationEntity>> {
        return decorationDao.getIslandDecorations(islandId)
    }

    suspend fun removeDecoration(decoration: DecorationEntity) {
        decorationDao.deleteDecoration(decoration)
    }

    suspend fun updateWeather(islandId: String, weather: String) {
        val island = islandDao.getIsland(islandId) ?: return
        islandDao.updateIsland(island.copy(weather = weather))
    }

    suspend fun updateTerrain(islandId: String, terrain: String) {
        val island = islandDao.getIsland(islandId) ?: return
        islandDao.updateIsland(island.copy(terrain = terrain))
    }
}
