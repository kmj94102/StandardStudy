package com.example.standardstudy.network.ktor

import com.example.standardstudy.network.ktor.data.User
import io.ktor.client.request.*
import kotlin.io.use

object UserRepo {
    suspend fun fetchUsers() : List<User> {
        val url = "https://62b5675b42c6473c4b31a4fb.mockapi.io/users"
//        return KtorClient.httpClient.get(url)

        return KtorClient.httpClient.use {
            it.get(url)
        }

    }
}