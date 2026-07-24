package com.birdempire.birds

import com.birdempire.core.GameConstants
import com.birdempire.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BirdSystem @Inject constructor() {
    private val birds = MutableStateFlow<List<BirdEntity>>(emptyList())

    companion object {
        val BIRD_SPECIES = mapOf(
            // Common
            BirdType.SPARROW to BirdSpecies(
                type = BirdType.SPARROW,
                name = "Sparrow",
                rarity = BirdRarity.COMMON,
                baseHealth = 50f,
                baseProduction = 1f,
                basePrice = 100,
                ability = "Quick Peck",
                description = "A small but quick bird",
                icon = "🐦"
            ),
            BirdType.BLUE_BIRD to BirdSpecies(
                type = BirdType.BLUE_BIRD,
                name = "Blue Bird",
                rarity = BirdRarity.COMMON,
                baseHealth = 60f,
                baseProduction = 1.2f,
                basePrice = 150,
                ability = "Melody",
                description = "A beautiful singing bird",
                icon = "🐦"
            ),
            BirdType.ROBIN to BirdSpecies(
                type = BirdType.ROBIN,
                name = "Robin",
                rarity = BirdRarity.COMMON,
                baseHealth = 55f,
                baseProduction = 1.1f,
                basePrice = 120,
                ability = "Hop",
                description = "A friendly garden bird",
                icon = "🐦"
            ),
            // Rare
            BirdType.FALCON to BirdSpecies(
                type = BirdType.FALCON,
                name = "Falcon",
                rarity = BirdRarity.RARE,
                baseHealth = 100f,
                baseProduction = 2f,
                basePrice = 500,
                ability = "Swift Strike",
                description = "A powerful predator",
                icon = "🦅"
            ),
            BirdType.OWL to BirdSpecies(
                type = BirdType.OWL,
                name = "Owl",
                rarity = BirdRarity.RARE,
                baseHealth = 90f,
                baseProduction = 2.2f,
                basePrice = 550,
                ability = "Night Vision",
                description = "A wise nocturnal bird",
                icon = "🦉"
            ),
            BirdType.PEACOCK to BirdSpecies(
                type = BirdType.PEACOCK,
                name = "Peacock",
                rarity = BirdRarity.RARE,
                baseHealth = 95f,
                baseProduction = 1.8f,
                basePrice = 600,
                ability = "Dazzle",
                description = "A magnificent display bird",
                icon = "🦚"
            ),
            // Epic
            BirdType.EAGLE to BirdSpecies(
                type = BirdType.EAGLE,
                name = "Eagle",
                rarity = BirdRarity.EPIC,
                baseHealth = 150f,
                baseProduction = 3f,
                basePrice = 1500,
                ability = "Royal Flight",
                description = "King of the skies",
                icon = "🦅"
            ),
            BirdType.GOLDEN_EAGLE to BirdSpecies(
                type = BirdType.GOLDEN_EAGLE,
                name = "Golden Eagle",
                rarity = BirdRarity.EPIC,
                baseHealth = 160f,
                baseProduction = 3.5f,
                basePrice = 2000,
                ability = "Golden Touch",
                description = "A legendary golden bird",
                icon = "🦅"
            ),
            // Legendary
            BirdType.PHOENIX to BirdSpecies(
                type = BirdType.PHOENIX,
                name = "Phoenix",
                rarity = BirdRarity.LEGENDARY,
                baseHealth = 250f,
                baseProduction = 5f,
                basePrice = 5000,
                ability = "Rebirth",
                description = "The legendary firebird",
                icon = "🔥"
            ),
            BirdType.CRYSTAL_PHOENIX to BirdSpecies(
                type = BirdType.CRYSTAL_PHOENIX,
                name = "Crystal Phoenix",
                rarity = BirdRarity.LEGENDARY,
                baseHealth = 280f,
                baseProduction = 6f,
                basePrice = 7500,
                ability = "Crystal Flame",
                description = "A phoenix made of pure crystal",
                icon = "💎"
            ),
            BirdType.DRAGON_BIRD to BirdSpecies(
                type = BirdType.DRAGON_BIRD,
                name = "Dragon Bird",
                rarity = BirdRarity.LEGENDARY,
                baseHealth = 300f,
                baseProduction = 7f,
                basePrice = 10000,
                ability = "Dragon's Roar",
                description = "A mythical dragon-bird hybrid",
                icon = "🐉"
            )
        )
    }

    fun getBirdSpecies(type: BirdType): BirdSpecies? = BIRD_SPECIES[type]

    fun createBird(playerId: String, type: BirdType, name: String = ""): BirdEntity {
        val species = BIRD_SPECIES[type] ?: return BirdEntity(
            birdId = generateBirdId(),
            playerId = playerId,
            birdType = type.name,
            name = name.ifEmpty { type.name }
        )

        return BirdEntity(
            birdId = generateBirdId(),
            playerId = playerId,
            birdType = type.name,
            name = name.ifEmpty { species.name },
            health = species.baseHealth,
            productionSpeed = species.baseProduction,
            price = species.basePrice,
            rarity = species.rarity.name,
            ability = species.ability
        )
    }

    fun feedBird(bird: BirdEntity): BirdEntity {
        return bird.copy(
            hunger = 100f,
            happiness = minOf(bird.happiness + 10f, 100f),
            lastFedAt = System.currentTimeMillis()
        )
    }

    fun collectEgg(bird: BirdEntity): Pair<BirdEntity, Long> {
        val eggReward = (bird.productionSpeed * 100).toLong()
        return Pair(
            bird.copy(lastProducedAt = System.currentTimeMillis()),
            eggReward
        )
    }

    fun upgradeBird(bird: BirdEntity, cost: Long): BirdEntity {
        val newLevel = bird.level + 1
        return bird.copy(
            level = newLevel,
            health = bird.health * 1.1f,
            productionSpeed = bird.productionSpeed * 1.15f,
            xp = 0
        )
    }

    fun breedBirds(bird1: BirdEntity, bird2: BirdEntity): BirdEntity? {
        // Simple breeding logic
        val successRate = GameConstants.BREEDING_SUCCESS_RATE
        val random = (0..100).random()

        return if (random <= successRate) {
            createBird(
                playerId = bird1.playerId,
                type = BirdType.valueOf(bird1.birdType),
                name = "${bird1.name} Jr."
            )
        } else null
    }

    private fun generateBirdId(): String = "bird_${System.currentTimeMillis()}_${(0..9999).random()}"
}
