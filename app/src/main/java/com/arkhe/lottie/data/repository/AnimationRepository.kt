package com.arkhe.lottie.data.repository

import com.arkhe.lottie.data.model.AnimationInfo
import kotlinx.coroutines.flow.Flow

interface AnimationRepository {
    suspend fun getAvailableAnimations(): Flow<Result<List<AnimationInfo>>>
    suspend fun downloadAnimation(url: String): Result<String>
}