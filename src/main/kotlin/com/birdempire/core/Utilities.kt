package com.birdempire.core

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

/**
 * Utility functions for BIRD EMPIRE
 */
object GameUtils {
    
    fun formatCurrency(amount: Long): String {
        return when {
            amount >= 1_000_000 -> String.format("%.1fM", amount / 1_000_000.0)
            amount >= 1_000 -> String.format("%.1fK", amount / 1_000.0)
            else -> amount.toString()
        }
    }

    fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        
        return when {
            hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, secs)
            minutes > 0 -> String.format("%02d:%02d", minutes, secs)
            else -> String.format("%02d", secs)
        }
    }

    fun getTimeUntilReset(): Long {
        val now = LocalDateTime.now()
        val nextReset = now.withHour(GameConstants.DAILY_MISSION_RESET_HOUR)
            .withMinute(0)
            .withSecond(0)
        
        return if (nextReset.isAfter(now)) {
            ChronoUnit.SECONDS.between(now, nextReset)
        } else {
            ChronoUnit.SECONDS.between(now, nextReset.plusDays(1))
        }
    }

    fun calculateProduction(baseProduction: Float, level: Int, bonusMultiplier: Float = 1f): Float {
        return baseProduction * (1 + (level - 1) * 0.1f) * bonusMultiplier
    }

    fun calculateUpgradeCost(baseCost: Long, currentLevel: Int): Long {
        return (baseCost * (1.15.pow(currentLevel))).toLong()
    }

    fun generateRandomId(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun getRandomBirdName(): String {
        val names = listOf(
            "Sky", "Phoenix", "Storm", "Crystal", "Golden", "Royal", "Mystic", "Thunder",
            "Flame", "Frost", "Emerald", "Sapphire", "Twilight", "Stellar", "Cosmic"
        )
        return names.random()
    }

    fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return kotlin.math.sqrt(
            (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)
        )
    }

    fun clamp(value: Float, min: Float, max: Float): Float {
        return when {
            value < min -> min
            value > max -> max
            else -> value
        }
    }

    fun lerp(start: Float, end: Float, t: Float): Float {
        return start + (end - start) * clamp(t, 0f, 1f)
    }
}

private fun Double.pow(exponent: Double): Double {
    return Math.pow(this, exponent)
}
