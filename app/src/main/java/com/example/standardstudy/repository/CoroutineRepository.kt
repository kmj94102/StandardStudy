package com.example.standardstudy.repository

import com.example.standardstudy.network.address.AddressClient
import com.example.standardstudy.network.address.data.AddressResult
import com.example.standardstudy.network.address.data.SearchAddressResult
import com.example.standardstudy.network.address.data.SearchResult
import java.lang.Exception
import javax.inject.Inject

class CoroutineRepository @Inject constructor(
    private val client : AddressClient
) {

   suspend fun selectAddress(keyword : String, currentPage: Int) : List<SearchAddressResult>? =
       try {
           val result = client.getAddress(keyword, currentPage)
           if (result.isSuccessful) {
               result.body()?.results?.toSearchResult()?.list
           } else {
               null
           }
       } catch (e: Exception) {
           e.printStackTrace()
           null
       }

}