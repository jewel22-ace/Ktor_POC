package com.example.myapplication.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val message : String,
    val success:Boolean,
    val user_id : String

)
