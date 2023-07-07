package com.example.myapplication.data.remote.dto


import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.util.toMap
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PostServiceImplTest {

    private lateinit var httpClientMock: HttpClient

    @Before
    fun setUp() {
        httpClientMock = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when (request.url.toString()) {
                        httpRoutes.VERSION -> {
                            respond(
                                "0.0.17-alpha.15",
                                HttpStatusCode.OK,
                                headersOf("Content-Type" to listOf(ContentType.Text.Plain.toString()))
                            )
                        }
                        httpRoutes.PROFILE_CHECK -> {
                            val testPhone = "+919083376728"
                            val testEmail = ""
                            val apiKey = "c49d7ce6-06af-406f-b7b5-e09c5e66d841"
                            val expectedRequestBody = """{"phone":"$testPhone","email":"$testEmail"}"""

                            val actualRequestBody = request.body.toByteArray().decodeToString()
                            assertEquals(HttpMethod.Post, request.method)
                            assertEquals(expectedRequestBody, actualRequestBody)

                            respond(
                                ProfileSuccessTestResponse,
                                HttpStatusCode.OK,
                                headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            )
                        }
                        httpRoutes.VALIDATE_TOKEN -> {
                            val authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY4ODM3MDI0NCwianRpIjoiN2ZiYWMzOTMtMjU0Ni00OWI2LTk4NzMtMTllNmY5Mjc0OGIxIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjY0OWE4OWU4OTAwOTFhNzI1N2IyNjhjNyIsIm5iZiI6MTY4ODM3MDI0NH0.HV_GjUarP9MHbpL7WKsGPIIR0n90h3j4RfhzLvt43kI"

                            val expectedAuthorizationHeader = "Bearer $authToken"
                            val actualAuthorizationHeader = request.headers[HttpHeaders.Authorization]

                            assertEquals(expectedAuthorizationHeader, actualAuthorizationHeader)


                            respond(
                                ValidateTokenTestResponse,
                                HttpStatusCode.OK,
                                headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                            )

                        }


                        else -> error("Unhandled ${request.url}")
                    }
                }

            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Test
    fun `test getVersion() with successful response`()= runBlocking {
        // Mock the dependencies

        val service = PostServiceImpl(httpClientMock)
        val version = service.getVersion()
        assertEquals("0.0.17-alpha.15", version)
    }

    @Test
    fun `test checkProfile() with successful response`()= runBlocking {
        // Mock the dependencies
        val testPhone = "+919083376728"
        val testEmail = ""
        val apiKey = "c49d7ce6-06af-406f-b7b5-e09c5e66d841"

        runBlocking {
            val response = httpClientMock.post<ProfileCheckResponse> {
                url(httpRoutes.PROFILE_CHECK)
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append("api-key", apiKey)
                }
                body = ProfileCheckBody(phone = testPhone, email = testEmail)
            }

            // Assert the response if needed
            assertEquals(ProfileCheckResponse("",true,false), response)
        }
    }
    @Test
    fun testValidateToken() {
        val authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY4ODM3MDI0NCwianRpIjoiN2ZiYWMzOTMtMjU0Ni00OWI2LTk4NzMtMTllNmY5Mjc0OGIxIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjY0OWE4OWU4OTAwOTFhNzI1N2IyNjhjNyIsIm5iZiI6MTY4ODM3MDI0NH0.HV_GjUarP9MHbpL7WKsGPIIR0n90h3j4RfhzLvt43kI"


        runBlocking {
            val response = httpClientMock.get<TokenResponse> {
                url(httpRoutes.VALIDATE_TOKEN)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $authToken")
                }
            }

            // Assert the response if needed
            assertEquals(TokenResponse("Token valid",true,"649a89e890091a7257b268c7"), response)
        }
    }
}







