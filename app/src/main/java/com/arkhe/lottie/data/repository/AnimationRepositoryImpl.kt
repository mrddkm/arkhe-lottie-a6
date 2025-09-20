package com.arkhe.lottie.data.repository

import com.arkhe.lottie.data.model.AnimationInfo
import com.arkhe.lottie.data.model.AnimationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class AnimationRepositoryImpl(
    private val httpClient: HttpClient
) : AnimationRepository {

    override suspend fun getAvailableAnimations(): Flow<Result<List<AnimationInfo>>> = flow {
        try {
            val response = httpClient.get("https://api.example.com/animations") {
                headers {
                    append("Content-Type", "application/json")
                }
            }

            val animationResponse: AnimationResponse = response.body()
            emit(Result.success(animationResponse.animations))
        } catch (e: Exception) {
            Timber.e(e, "Error loading animations")
            emit(Result.failure(e))
        }
    }

    override suspend fun downloadAnimation(url: String): Result<String> {
        return try {
            val response = httpClient.get(url)
            val jsonString: String = response.body()
            Result.success(jsonString)
        } catch (e: Exception) {
            Timber.e(e, "Error downloading animation")
            Result.failure(e)
        }
    }
}