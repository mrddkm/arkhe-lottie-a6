package com.arkhe.lottie.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AnimationInfo(
    val id: String,
    val name: String,
    val url: String,
    val description: String? = null,
    val size: Long? = null,
    val duration: Int? = null
)

@Serializable
data class AnimationResponse(
    val animations: List<AnimationInfo>
)