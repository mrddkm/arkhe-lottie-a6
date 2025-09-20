package com.arkhe.lottie.domain.usecase

import com.arkhe.lottie.data.model.AnimationInfo
import com.arkhe.lottie.data.repository.AnimationRepository
import kotlinx.coroutines.flow.Flow

class LoadAnimationUseCase(
    private val repository: AnimationRepository
) {
    suspend fun getAnimations(): Flow<Result<List<AnimationInfo>>> {
        return repository.getAvailableAnimations()
    }

    suspend fun downloadAnimation(url: String): Result<String> {
        return repository.downloadAnimation(url)
    }
}
