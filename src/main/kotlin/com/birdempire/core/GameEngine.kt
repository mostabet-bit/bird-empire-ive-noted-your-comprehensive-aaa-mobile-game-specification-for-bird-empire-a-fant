package com.birdempire.core

import kotlinx.coroutines.*
import java.time.LocalDateTime
import kotlin.math.pow

/**
 * Main game engine managing core game loop and state
 */
class GameEngine {
    private var gameLoopJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var lastUpdateTime = LocalDateTime.now()
    private var deltaTime = 0f

    fun startGameLoop() {
        gameLoopJob = scope.launch {
            while (isActive) {
                val currentTime = LocalDateTime.now()
                deltaTime = calculateDeltaTime(lastUpdateTime, currentTime)
                lastUpdateTime = currentTime

                update(deltaTime)
                delay(16) // ~60 FPS
            }
        }
    }

    fun stopGameLoop() {
        gameLoopJob?.cancel()
    }

    private suspend fun update(deltaTime: Float) {
        // Main game update loop
        // This will be called by game systems
    }

    private fun calculateDeltaTime(lastTime: LocalDateTime, currentTime: LocalDateTime): Float {
        return (currentTime.second - lastTime.second + 
                (currentTime.nano - lastTime.nano) / 1_000_000_000f).coerceAtLeast(0f)
    }

    fun calculateXPForLevel(level: Int): Int {
        return (GameConstants.XP_PER_LEVEL * 
                GameConstants.XP_LEVEL_MULTIPLIER.pow((level - 1).toDouble())).toInt()
    }

    fun calculateLevelFromXP(totalXP: Long): Int {
        var level = 1
        var xpRequired = 0L
        while (xpRequired + calculateXPForLevel(level) <= totalXP && level < GameConstants.MAX_LEVEL) {
            xpRequired += calculateXPForLevel(level).toLong()
            level++
        }
        return level
    }

    fun onDestroy() {
        scope.cancel()
    }
}
