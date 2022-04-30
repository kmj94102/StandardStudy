package com.example.standardstudy.repository

import com.example.standardstudy.network.address.AddressClient
import com.example.standardstudy.network.address.data.AddressResult
import java.lang.Exception
import javax.inject.Inject

class CoroutineRepository @Inject constructor(
    private val client : AddressClient
) {

   suspend fun selectAddress(keyword : String, currentPage: Int) : AddressResult? =
       try {
           val result = client.getAddress(keyword, currentPage)
           if (result.isSuccessful) {
               result.body()
           } else {
               null
           }
       } catch (e: Exception) {
           e.printStackTrace()
           null
       }

}