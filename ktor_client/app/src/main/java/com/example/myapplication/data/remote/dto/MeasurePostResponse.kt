package com.example.myapplication.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MeasurePostResponse(
    val measure: MeasureData,
    val success: Boolean
)

@Serializable
data class MeasureData(
    val count: Int,
    val measure: List<Measure>
)

@Serializable
data class Measure(
    val absorbance: Double,
    val albumin: Double,
    val assayType: String,
    val calibrationId: String,
    val createdAt: String,
    val creatinine: Double,
    val fluorescence: Double,
    val id: String,
    val patientId: String,
    val person: String,
    val readUuid: String,
    val syncedAt: String,
    val userId: String
)
