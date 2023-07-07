package com.example.myapplication.data.remote.dto

import kotlinx.serialization.Serializable
@Serializable
data class MeasurePostBody(
    val search: String,
    val filter: Filter,
    val sort: String
)
@Serializable
data class Filter(
    val albumin: List<String>,
    val creatinine: List<String>,
    val assay_type: List<String>,
    val date: List<String>
)
