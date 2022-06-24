package com.example.standardstudy.network.ktor.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val createdAt : String,
    val name : String,
    val avatar : String,
    val id : String
)