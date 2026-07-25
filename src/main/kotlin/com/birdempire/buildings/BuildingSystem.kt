package com.birdempire.buildings

import com.birdempire.data.models.BuildingEntity
import javax.inject.Inject
import javax.inject.Singleton

enum class BuildingType {
    BIRD_CASTLE, BIRD_HOUSE, FOOD_FARM, EGG_FACTORY,
    MARKET, RESEARCH_CENTER, TRAINING_AREA, MAGIC_TOWER
}

@Singleton
class BuildingSystem @Inject constructor() {

    companion object {
        val BUILDING_TEMPLATES = mapOf(
            BuildingType.BIRD_CASTLE to BuildingTemplate(
                type = BuildingType.BIRD_CASTLE,
                name = "Bird Castle",
                description = "Your main castle",
                baseCost = 5000,
                productionBonus = 1.5f,
                icon = "🏰"
            ),
            BuildingType.BIRD_HOUSE to BuildingTemplate(
                type = BuildingType.BIRD_HOUSE,
                name = "Bird House",
                description = "Home for your birds",
                baseCost = 500,
                productionBonus = 1.1f,
                icon = "🏠"
            ),
            BuildingType.FOOD_FARM to BuildingTemplate(
                type = BuildingType.FOOD_FARM,
                name = "Food Farm",
                description = "Grow food for birds",
                baseCost = 1000,
                productionBonus = 1.2f,
                icon = "🌾"
            ),
            BuildingType.EGG_FACTORY to BuildingTemplate(
                type = BuildingType.EGG_FACTORY,
                name = "Egg Factory",
                description = "Increase egg production",
                baseCost = 2000,
                productionBonus = 1.8f,
                icon = "🏭"
            ),
            BuildingType.MARKET to BuildingTemplate(
                type = BuildingType.MARKET,
                name = "Market",
                description = "Trade with others",
                baseCost = 3000,
                productionBonus = 1.0f,
                icon = "🏪"
            ),
            BuildingType.RESEARCH_CENTER to BuildingTemplate(
                type = BuildingType.RESEARCH_CENTER,
                name = "Research Center",
                description = "Unlock new technologies",
                baseCost = 4000,
                productionBonus = 1.3f,
                icon = "🔬"
            ),
            BuildingType.TRAINING_AREA to BuildingTemplate(
                type = BuildingType.TRAINING_AREA,
                name = "Training Area",
                description = "Train your birds",
                baseCost = 2500,
                productionBonus = 1.4f,
                icon = "⚔️"
            ),
            BuildingType.MAGIC_TOWER to BuildingTemplate(
                type = BuildingType.MAGIC_TOWER,
                name = "Magic Tower",
                description = "Harness magical power",
                baseCost = 5000,
                productionBonus = 2.0f,
                icon = "🔮"
            )
        )
    }

    fun createBuilding(
        playerId: String,
        islandId: String,
        type: BuildingType,
        posX: Float = 0f,
        posY: Float = 0f
    ): BuildingEntity {
        val template = BUILDING_TEMPLATES[type] ?: return BuildingEntity(
            buildingId = generateBuildingId(),
            playerId = playerId,
            islandId = islandId,
            type = type.name,
            level = 1,
            upgradeCost = 1000
        )

        return BuildingEntity(
            buildingId = generateBuildingId(),
            playerId = playerId,
            islandId = islandId,
            type = type.name,
            name = template.name,
            level = 1,
            upgradeCost = template.baseCost.toLong(),
            productionBonus = template.productionBonus,
            posX = posX,
            posY = posY,
            createdAt = System.currentTimeMillis()
        )
    }

    fun upgradeBuilding(building: BuildingEntity): BuildingEntity {
        val newLevel = building.level + 1
        return building.copy(
            level = newLevel,
            upgradeCost = (building.upgradeCost * 1.2).toLong(),
            productionBonus = building.productionBonus * 1.1f
        )
    }

    fun getUpgradeCost(building: BuildingEntity): Long {
        return (building.upgradeCost * (building.level * 0.5)).toLong()
    }

    private fun generateBuildingId(): String = "building_${System.currentTimeMillis()}_${(0..9999).random()}"
}

data class BuildingTemplate(
    val type: BuildingType,
    val name: String,
    val description: String,
    val baseCost: Int,
    val productionBonus: Float,
    val icon: String
)
