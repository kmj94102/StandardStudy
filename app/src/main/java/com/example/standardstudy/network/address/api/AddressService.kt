package com.example.standardstudy.network.address.api

import com.example.standardstudy.BuildConfig
import com.example.standardstudy.network.address.data.AddressParam
import com.example.standardstudy.network.address.data.AddressResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AddressService {

    @POST("addrLinkApiJsonp.do/")
    suspend fun getAddress(@Body params: AddressParam) : Response<AddressResult>

    @GET("addrlink/addrLinkApi.do")
    suspend fun getAddress(
        @Query("confmKey") confmKey : String? = BuildConfig.ADDRESS_API_KEY,
        @Query("currentPage") currentPage : Int? = 1,
        @Query("countPerPage") countPerPage : Int? = 10,
        @Query("keyword") keyword : String,
        @Query("resultType") resultType : String? = "json",
        @Query("firstSort") firstSort : String? = "road"
    ): Response<AddressResult>

    @GET("addrlink/addrLinkApi.do")
    fun getAddress2(
        @Query("confmKey") confmKey : String? = BuildConfig.ADDRESS_API_KEY,
        @Query("currentPage") currentPage : Int? = 1,
        @Query("countPerPage") countPerPage : Int? = 10,
        @Query("keyword") keyword : String,
        @Query("resultType") resultType : String? = "json",
        @Query("firstSort") firstSort : String? = "road"
    ): Call<AddressResult>

}