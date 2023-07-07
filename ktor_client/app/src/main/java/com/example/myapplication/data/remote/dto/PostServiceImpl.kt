package com.example.myapplication.data.remote.dto

import android.util.Log
import android.widget.Toast
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.features.get
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlin.coroutines.coroutineContext

class PostServiceImpl (
    private val client : HttpClient
    ): PostService {
    override suspend fun checkProfile(): ProfileCheckResponse? {
        return try {
            val p_body= ProfileCheckBody(phone = "+919083376728", email = "")
            val apiKey = "c49d7ce6-06af-406f-b7b5-e09c5e66d841"
            Log.d("Mosaif", "This is a debug message")
            client.post<ProfileCheckResponse> {
                Log.d("Mosaif", httpRoutes.PROFILE_CHECK)
                url(httpRoutes.PROFILE_CHECK)
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append("api-key", apiKey)

                }
                body =p_body
            }

        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("ErrorRedirect: ${e.response.status.description}")

            null
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("ErrorClientResponse: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("ErrorServerResponse: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            println("ErrorExceptionInCode: ${e.message}")
            null
        }
    }

    override suspend fun validateToken(): TokenResponse? {
        return try {
            val authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY4ODM3MDI0NCwianRpIjoiN2ZiYWMzOTMtMjU0Ni00OWI2LTk4NzMtMTllNmY5Mjc0OGIxIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjY0OWE4OWU4OTAwOTFhNzI1N2IyNjhjNyIsIm5iZiI6MTY4ODM3MDI0NH0.HV_GjUarP9MHbpL7WKsGPIIR0n90h3j4RfhzLvt43kI"
            client.get<TokenResponse>{
                url(httpRoutes.VALIDATE_TOKEN)
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization,"Bearer $authToken")

                }
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("ErrorRedirect: ${e.response.status.description}")

            null
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("ErrorClientResponse: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("ErrorServerResponse: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            println("ErrorExceptionInCode: ${e.message}")
            null
        }
    }

    override suspend fun getMeasures(): MeasurePostResponse? {
        return try {
            val filter = Filter(
                albumin = emptyList(),
                creatinine = emptyList(),
                assay_type = emptyList(),
                date = emptyList()
            )

            val requestBody = MeasurePostBody(
                search = "",
                filter = filter,
                sort = ""
            )
            val authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY4ODM3MDI0NCwianRpIjoiN2ZiYWMzOTMtMjU0Ni00OWI2LTk4NzMtMTllNmY5Mjc0OGIxIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjY0OWE4OWU4OTAwOTFhNzI1N2IyNjhjNyIsIm5iZiI6MTY4ODM3MDI0NH0.HV_GjUarP9MHbpL7WKsGPIIR0n90h3j4RfhzLvt43kI"
            client.post {
                url(httpRoutes.MEASURE)
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, "Bearer $authToken")
                }
                body =requestBody
            }
        }catch(e: RedirectResponseException) {
            // 3xx - responses
            println("ErrorRedirect: ${e.response.status.description}")

            null
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("ErrorClientResponse: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("ErrorServerResponse: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            println("ErrorExceptionInCode: ${e.message}")
            null
        }
    }

    override suspend fun getVersion(): String? {
            return try {
                client.get {
                    url(httpRoutes.VERSION)
                }

            }catch(e: RedirectResponseException) {
                // 3xx - responses
                println("Error: ${e.response.status.description}")
                null
            } catch(e: ClientRequestException) {
                // 4xx - responses
                println("Error: ${e.response.status.description}")
                null
            } catch(e: ServerResponseException) {
                // 5xx - responses
                println("Error: ${e.response.status.description}")
                null
            } catch(e: Exception) {
                println("Error: ${e.message}")
                null
            }
    }
}