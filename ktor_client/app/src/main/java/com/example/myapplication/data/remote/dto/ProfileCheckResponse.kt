package com.example.myapplication.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileCheckResponse(

    val message: String,
    val success: Boolean,
    val user_exists: Boolean

)
