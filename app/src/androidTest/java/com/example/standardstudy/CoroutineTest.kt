package com.example.standardstudy

import kotlinx.coroutines.*

fun main() = runBlocking {

    doWold()

    println("Hello")

}
//    val elapsed = measureTimeMillis {
//     println("작업 시간 : $elapsed")
suspend fun doWold() = coroutineScope {
    delay(1000L)
    println("World!")
}