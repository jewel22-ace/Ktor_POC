package com.example.myapplication.data.remote.dto

object httpRoutes {

    const val BASE_URL="https://86ee-2a09-bac5-3add-1eb-00-31-112.ngrok-free.app"
    const val VERSION="$BASE_URL/version"
    const val MEASURE = "$BASE_URL//api/v1/user/649a89e890091a7257b268c7/measure_filter"
    const val PROFILE_CHECK = "$BASE_URL/api/v1/profile/check"
    const val VALIDATE_TOKEN = "$BASE_URL/api/v1/token/validate"
}