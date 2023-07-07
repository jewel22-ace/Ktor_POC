package com.example.myapplication.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileCheckBody(
    val phone : String,
    val email : String
)
