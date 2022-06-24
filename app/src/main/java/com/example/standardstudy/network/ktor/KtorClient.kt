package com.example.standardstudy.network.ktor

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object KtorClient {

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    // http 클라이언트
    val httpClient = HttpClient {
        // Json 설정
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = json)
        }

        // logging 설정
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("TAG", "api log : $message")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 1_000 * 10
            connectTimeoutMillis = 1_000 * 10
            socketTimeoutMillis = 1_000 * 10
        }

        // 기본적인 api 호출 시 넣는 것들 즉 기본 셋팅
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

    }

}