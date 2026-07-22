package com.birdempire.core

/**
 * Global game constants for BIRD EMPIRE
 */
object GameConstants {
    // Game Info
    const val GAME_NAME = "BIRD EMPIRE"
    const val GAME_VERSION = "1.0.0"
    const val MIN_LEVEL = 1
    const val MAX_LEVEL = 100

    // Player
    const val STARTING_COINS = 1000
    const val STARTING_GEMS = 50
    const val STARTING_SKY_CRYSTALS = 10
    const val STARTING_LEVEL = 1
    const val STARTING_XP = 0

    // Island
    const val MAX_ISLANDS = 10
    const val MAX_BUILDINGS_PER_ISLAND = 20
    const val MAX_BIRDS_PER_ISLAND = 50

    // Bird Production
    const val EGG_PRODUCTION_INTERVAL_SECONDS = 30L
    const val BIRD_HUNGER_RATE = 0.5f // Per minute
    const val BIRD_HAPPINESS_RATE = 0.3f // Per minute

    // Economy
    const val COIN_MULTIPLIER = 1.0
    const val GEM_PREMIUM_PRICE = 9.99
    const val MARKET_TAX_PERCENTAGE = 5

    // XP
    const val XP_PER_LEVEL = 1000
    const val XP_LEVEL_MULTIPLIER = 1.1

    // Breeding
    const val BREEDING_COOLDOWN_MINUTES = 60L
    const val BREEDING_SUCCESS_RATE = 75

    // Missions
    const val DAILY_MISSION_RESET_HOUR = 0
    const val MAX_DAILY_MISSIONS = 5
    const val MISSION_REWARD_XP = 100
    const val MISSION_REWARD_COINS = 500

    // Cache
    const val CACHE_DURATION_MINUTES = 5L

    // API
    const val API_TIMEOUT_SECONDS = 30L
    const val API_BASE_URL = "https://api.birdempire.com/v1/"
    const val API_VERSION = "1.0"

    // Database
    const val DATABASE_NAME = "bird_empire.db"
    const val DATABASE_VERSION = 1

    // UI
    const val ANIMATION_DURATION_MS = 300
    const val LONG_ANIMATION_DURATION_MS = 500
}
