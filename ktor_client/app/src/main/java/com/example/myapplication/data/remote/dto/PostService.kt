package com.example.myapplication.data.remote.dto

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging

interface PostService {

    suspend fun getVersion(): String?

    suspend fun getMeasures() : MeasurePostResponse?

    suspend fun checkProfile() : ProfileCheckResponse?

    suspend fun validateToken() : TokenResponse?

    companion object{
        fun create():PostService{
            return PostServiceImpl(
                client = HttpClient(Android){
                    install(Logging){
                        level=LogLevel.ALL
                    }
                    install(JsonFeature){
                        serializer =KotlinxSerializer()
                    }
                }
            )
        }
    }
}