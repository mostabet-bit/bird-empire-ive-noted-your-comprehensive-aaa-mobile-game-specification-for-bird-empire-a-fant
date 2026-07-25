package com.birdempire.network

import com.birdempire.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthResponse>

    // Player
    @GET("players/{playerId}")
    suspend fun getPlayer(@Path("playerId") playerId: String): Response<PlayerProfile>

    @PUT("players/{playerId}")
    suspend fun updatePlayer(
        @Path("playerId") playerId: String,
        @Body player: PlayerEntity
    ): Response<PlayerProfile>

    @GET("players/{playerId}/achievements")
    suspend fun getAchievements(@Path("playerId") playerId: String): Response<List<AchievementEntity>>

    // Birds
    @GET("players/{playerId}/birds")
    suspend fun getPlayerBirds(@Path("playerId") playerId: String): Response<List<BirdEntity>>

    @POST("players/{playerId}/birds")
    suspend fun createBird(
        @Path("playerId") playerId: String,
        @Body bird: BirdEntity
    ): Response<BirdEntity>

    @PUT("birds/{birdId}")
    suspend fun updateBird(
        @Path("birdId") birdId: String,
        @Body bird: BirdEntity
    ): Response<BirdEntity>

    // Island
    @GET("players/{playerId}/islands")
    suspend fun getPlayerIslands(@Path("playerId") playerId: String): Response<List<IslandEntity>>

    @POST("players/{playerId}/islands")
    suspend fun createIsland(
        @Path("playerId") playerId: String,
        @Body island: IslandEntity
    ): Response<IslandEntity>

    @PUT("islands/{islandId}")
    suspend fun updateIsland(
        @Path("islandId") islandId: String,
        @Body island: IslandEntity
    ): Response<IslandEntity>

    // Buildings
    @GET("islands/{islandId}/buildings")
    suspend fun getBuildings(@Path("islandId") islandId: String): Response<List<BuildingEntity>>

    @POST("islands/{islandId}/buildings")
    suspend fun createBuilding(
        @Path("islandId") islandId: String,
        @Body building: BuildingEntity
    ): Response<BuildingEntity>

    // Market
    @GET("market/listings")
    suspend fun getMarketListings(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<List<MarketListingEntity>>

    @POST("market/listings")
    suspend fun createListing(@Body listing: MarketListingEntity): Response<MarketListingEntity>

    @DELETE("market/listings/{listingId}")
    suspend fun cancelListing(@Path("listingId") listingId: String): Response<Unit>

    @POST("market/purchase")
    suspend fun purchaseListing(@Body purchase: PurchaseRequest): Response<TransactionEntity>

    // Missions
    @GET("players/{playerId}/missions")
    suspend fun getMissions(@Path("playerId") playerId: String): Response<List<MissionEntity>>

    @PUT("missions/{missionId}/progress")
    suspend fun updateMissionProgress(
        @Path("missionId") missionId: String,
        @Body progress: MissionProgressRequest
    ): Response<MissionEntity>

    @POST("missions/{missionId}/claim")
    suspend fun claimMissionReward(@Path("missionId") missionId: String): Response<MissionEntity>

    // Leaderboard
    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 100
    ): Response<List<LeaderboardEntry>>

    @GET("leaderboard/player/{playerId}")
    suspend fun getPlayerRank(@Path("playerId") playerId: String): Response<LeaderboardEntry>

    // Events
    @GET("events/active")
    suspend fun getActiveEvents(): Response<List<EventEntity>>

    @POST("events/{eventId}/join")
    suspend fun joinEvent(
        @Path("eventId") eventId: String,
        @Body request: JoinEventRequest
    ): Response<EventEntity>
}

// Request/Response DTOs
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val playerId: String,
    val expiresIn: Long
)

data class RefreshTokenRequest(
    val refreshToken: String
)

data class PurchaseRequest(
    val listingId: String,
    val quantity: Int
)

data class MissionProgressRequest(
    val progress: Int
)

data class JoinEventRequest(
    val playerId: String
)
