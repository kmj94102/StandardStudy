package com.example.standardstudy.workmanager

import com.example.standardstudy.network.address.AddressClient
import com.example.standardstudy.network.address.data.AddressParam
import com.example.standardstudy.network.address.data.AddressResult
import javax.inject.Inject

class WorkMangerRepository @Inject constructor(
    private val client: AddressClient
) {

    suspend fun getAddress(keyword : String, currentPage: Int) : AddressResult? {
        return try {
            val response = client.getAddress(keyword, currentPage)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

}