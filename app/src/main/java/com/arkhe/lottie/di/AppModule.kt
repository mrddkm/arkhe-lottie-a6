package com.arkhe.lottie.di

import com.arkhe.lottie.data.repository.AnimationRepository
import com.arkhe.lottie.data.repository.AnimationRepositoryImpl
import com.arkhe.lottie.domain.usecase.LoadAnimationUseCase
import com.arkhe.lottie.presentation.viewmodel.LottieLoadingViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Network - Ktor Client
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.INFO
            }
        }
    }

    // Repository
    single<AnimationRepository> {
        AnimationRepositoryImpl(httpClient = get())
    }

    // Use Cases
    single { LoadAnimationUseCase(repository = get()) }

    // ViewModel
    @Suppress("DEPRECATION")
    viewModel { LottieLoadingViewModel(loadAnimationUseCase = get()) }
}