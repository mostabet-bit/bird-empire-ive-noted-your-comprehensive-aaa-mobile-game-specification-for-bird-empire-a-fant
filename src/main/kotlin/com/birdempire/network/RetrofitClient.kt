package com.birdempire.network

import com.birdempire.core.GameConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getApiService(): ApiService {
        if (retrofit == null) {
            retrofit = createRetrofit()
        }
        return retrofit!!.create(ApiService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(GameConstants.API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(GameConstants.API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(GameConstants.API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    .header("X-API-Version", GameConstants.API_VERSION)
                    .header("User-Agent", "BirdEmpire/${GameConstants.GAME_VERSION}")

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(GameConstants.API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
