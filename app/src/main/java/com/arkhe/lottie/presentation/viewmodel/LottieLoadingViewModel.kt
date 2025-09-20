package com.arkhe.lottie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.lottie.data.model.AnimationInfo
import com.arkhe.lottie.domain.usecase.LoadAnimationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LottieUiState(
    val isLoading: Boolean = false,
    val animations: List<AnimationInfo> = emptyList(),
    val currentAnimation: String? = null,
    val error: String? = null,
    val downloadProgress: Float = 0f
)

class LottieLoadingViewModel(
    private val loadAnimationUseCase: LoadAnimationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LottieUiState())
    val uiState: StateFlow<LottieUiState> = _uiState.asStateFlow()

    init {
        loadAvailableAnimations()
    }

    private fun loadAvailableAnimations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            loadAnimationUseCase.getAnimations()
                .catch { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Unknown error"
                        )
                    }
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { animations ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    animations = animations,
                                    error = null
                                )
                            }
                        },
                        onFailure = { exception ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = exception.message ?: "Failed to load animations"
                                )
                            }
                        }
                    )
                }
        }
    }

    fun downloadAnimation(animationInfo: AnimationInfo) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    downloadProgress = 0f,
                    error = null
                )
            }

            // Simulate progress updates
            for (progress in 0..100 step 10) {
                _uiState.update { it.copy(downloadProgress = progress / 100f) }
                kotlinx.coroutines.delay(100)
            }

            val result = loadAnimationUseCase.downloadAnimation(animationInfo.url)

            result.fold(
                onSuccess = { jsonString ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            currentAnimation = jsonString,
                            downloadProgress = 1f,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to download animation",
                            downloadProgress = 0f
                        )
                    }
                }
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun retry() {
        loadAvailableAnimations()
    }
}